const boardCategory = {
    currentBoardType: "ALL",
    prevButton: document.querySelector("#ALL"),
    currentButton: "",
    init: function () {
        boardCategory.initCategoryButton();
    },
    initCategoryButton: function () {
        const categoryList = document.querySelector("#category");
        categoryList.addEventListener("click", (evt) => {
            boardCategory.changeBoardTypeButton(evt.target);
            boardCategory.changeBoardType(evt.target);
            boardList.requestBoardList(evt.target);
        });
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
    init: function (boardPageData) {
        boardList.createBoardList(boardPageData._embedded.boardReadResponseDtoList);
    },
    requestBoardList: async function(clickedCategoryButton){
        if (clickedCategoryButton.classList.contains("nav-link")){
            const boardPageData = await getData("/api/boards?boardType=" + clickedCategoryButton.id);
            console.log(boardPageData);
            // boardPage.init(boardPageData);
            boardList.createBoardList(boardPageData._embedded.boardReadResponseDtoList);
        }
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

document.addEventListener("DOMContentLoaded", async () => {
    const boardPageData = await getData("/api/boards");
    console.log(boardPageData);
    boardCategory.init();
    boardList.init(boardPageData);
});