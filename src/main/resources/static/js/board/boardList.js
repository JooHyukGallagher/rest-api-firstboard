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
            boardList.init(boardReadResource);
            // TODO: 카테고리에 따른 페이징 버튼 초기화 로직 추가
            boardPage.init(boardReadResource);
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
        console.log(boardPageData);
        const pageInfo = boardPageData.page;
        const links = boardPageData._links;
        const boardReadResponseDtoList = boardPageData._embedded.boardReadResponseDtoList

        console.log(boardReadResponseDtoList);

        boardPage.makePageButton(links, pageInfo);
        boardPage.pageButtonAction(pageInfo);
    },
    pageButtonAction: function (pageInfo) {
      const pageButton = document.querySelector(".pagination");
      pageButton.addEventListener("click", async (evt) => {
          const pageNum = evt.target.parentNode.id;
          let requestUrl = "/api/boards?page=" + pageNum + "&size=" + pageInfo.size + "&boardType=" + boardCategory.currentBoardType;
          const boardReadResource = await getData(requestUrl);
          boardList.init(boardReadResource);
          boardPage.makePageButton(links, pageInfo);
      });
    },
    makePageButton: function (links, pageInfo) {
        let endPage = Math.ceil((pageInfo.number + 1) / pageInfo.size) * pageInfo.size;
        let startPage = (endPage - pageInfo.size) + 1;
        let tempEndPage = Math.ceil(pageInfo.totalElements/pageInfo.size);
        if(endPage > tempEndPage){
            endPage = tempEndPage;
        }
        let prev = startPage !== 1;
        let next = endPage * pageInfo.size < pageInfo.totalElements;

        let pageButtonListContainer = document.querySelector(".pagination");
        // 초기화
        pageButtonListContainer.innerHTML = "";
        // 처음 페이지 버튼 생성
        let firstPageButton = document.querySelector("#firstPageButtonTemplate").innerHTML;
        pageButtonListContainer.innerHTML += firstPageButton;

        if(prev){
            let prevPageButton = document.querySelector("#prevPageButtonTemplate").innerHTML;
            pageButtonListContainer.innerHTML += prevPageButton;
        }

        // 페이지 버튼 생성
        for (let i = 0; i < endPage; i++) {
            const pageButtonTemplate = document.querySelector("#pageButtonTemplate").innerHTML;
            const bindTemplate = Handlebars.compile(pageButtonTemplate);

            const data = {
                realPage: i,
                currentPage: i + 1
            };

            const result = bindTemplate(data);
            pageButtonListContainer.innerHTML += result;
        }

        if (next){
            let nextPageButton = document.querySelector("#nextPageButtonTemplate").innerHTML;
            pageButtonListContainer.innerHTML += nextPageButton;
        }

        // 마지막 페이지 버튼 생성
        let lastPageButton = document.querySelector("#lastPageButtonTemplate").innerHTML;
        pageButtonListContainer.innerHTML += lastPageButton;
    }
};

document.addEventListener("DOMContentLoaded", async () => {
    const boardReadResource = await getData("/api/boards");
    boardCategory.init();
    boardList.init(boardReadResource);
    boardPage.init(boardReadResource);
});