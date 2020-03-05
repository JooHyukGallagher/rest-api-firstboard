const boardCategory = {
    currentBoardType: "ALL",
    prevButton: document.querySelector("#ALL"),
    currentButton: "",
    init: function () {
        const categoryList = document.querySelector("#category");
        categoryList.addEventListener("click", boardCategory.initCategoryButton);
    },
    initCategoryButton: async function (evt) {
        let clickedButton = evt.target;
        if (clickedButton.classList.contains("nav-link")) {
            boardCategory.changeBoardTypeButton(clickedButton);
            boardCategory.changeBoardType(clickedButton);

            let requestUrl = "/api/boards?boardType=" + clickedButton.id;
            const boardReadResource = await getData(requestUrl);
            boardList.createBoardList(boardReadResource._embedded.boardReadResponseDtoList);
            // TODO: 카테고리에 따른 페이징 버튼 초기화 로직 추가
        }
    },
    changeBoardTypeButton: function (clickedCategoryButton) {
        boardCategory.prevButton.classList.remove("active");
        boardCategory.currentButton = clickedCategoryButton;
        boardCategory.currentButton.classList.add("active");
        boardCategory.prevButton = clickedCategoryButton;
    },
    changeBoardType: function (clickedCategoryButton) {
        boardCategory.currentBoardType = clickedCategoryButton.id;
    }
};

const boardList = {
    init: function (boardReadResource) {
        boardList.createBoardList(boardReadResource._embedded.boardReadResponseDtoList);
    },
    createBoardList(boardListResponseList) {
        // 기존 리스트 삭제
        const boardListContainer = document.querySelector("#tbody");
        boardListContainer.innerHTML = "";

        boardListResponseList.forEach(boardListResponse => {
            const data = boardList.createBindingData(boardListResponse);
            const boardListTemplate = document.querySelector("#boardListTemplate").innerHTML;

            const bindTemplate = Handlebars.compile(boardListTemplate);
            const result = bindTemplate(data);
            boardListContainer.innerHTML += result;
        });
    },
    createBindingData: function (board) {
        return {
            id: board.id,
            boardTitle: board.boardTitle,
            replyCount: board.replyCount,
            boardType: board.boardType,
            name: board.name,
            viewCount: board.viewCount,
            createDate: board.createDate,
            modifyDate: board.modifyDate,
        };
    }
};

const boardPage = {
    init: function (boardPageData) {
        const pageInfo = boardPageData.page;
        const links = boardPageData._links;
        const boardReadResponseDtoList = boardPageData._embedded.boardReadResponseDtoList

        console.log(boardReadResponseDtoList);
        console.log(pageInfo);
        console.log(links);
        boardPage.createPageButtonList(links, pageInfo);
    },
    createPageButtonList(links, pageInfo) {
        let paginationContainer = document.querySelector(".pagination");

        if (pageInfo.number === 0) {
            document.querySelector("#prevPageButton").classList.add("disabled");
        } else if (pageInfo.number === pageInfo.totalPages - 1) {
            document.querySelector("#nextPageButton").classList.add("disabled");
        }
    },
    makePageButtonTemplate: function (links, pageInfo) {

    }
};

document.addEventListener("DOMContentLoaded", async () => {
    const boardReadResource = await getData("/api/boards");
    boardCategory.init();
    boardList.init(boardReadResource);
    boardPage.init(boardReadResource);
});