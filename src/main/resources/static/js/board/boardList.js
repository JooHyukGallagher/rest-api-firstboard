
const boardCategory = {
    currentBoardType: "ALL",
    prevButton: document.querySelector("#ALL"),
    currentButton: "",
    init: function () {
        this.registCategoryButton();
    },
    registCategoryButton: function () {
        const categoryList = document.querySelector("#category");
        categoryList.addEventListener("click", (evt) => {
            boardCategory.changeBoardTypeButton(evt.target);
            boardCategory.changeBoardType(evt.target);
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

document.addEventListener("DOMContentLoaded", async () => {
    const boardPageData = await getData("/api/boards");
    console.log(boardPageData);
    boardCategory.init();
});