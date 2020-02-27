const getData = (url) => {
    return fetch(url)
        .then(response => response.json())
        .catch(err => alert(JSON.stringify(err)));
};

const postData = (url, data) => {
    // Default options are marked with *
    return fetch(url, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        headers: {
            'Content-Type': 'application/json',
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: JSON.stringify(data) // body data type must match "Content-Type" header
    })
        .then(response => {
            alert("등록되었습니다.");
            return response.json()
        }) // parses JSON response into native JavaScript objects
        .catch(err => alert(JSON.stringify(err)));
};

const putData = (url, data) => {
    // Default options are marked with *
    return fetch(url, {
        method: 'PUT', // *GET, POST, PUT, DELETE, etc.
        headers: {
            'Content-Type': 'application/json',
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: JSON.stringify(data) // body data type must match "Content-Type" header
    })
        .then(response => {
            alert("수정 되었습니다.");
            return response.json()
        }) // parses JSON response into native JavaScript objects
        .catch(err => alert(JSON.stringify(err)));
};

const removeData = (url) => {
    // Default options are marked with *
    return fetch(url, {
        method: 'DELETE', // *GET, POST, PUT, DELETE, etc.
        headers: {
            'Content-Type': 'application/json',
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
    })
        .then(response => {
            alert("삭제 되었습니다.");
            return response.json()
        }) // parses JSON response into native JavaScript objects
        .catch(err => alert(JSON.stringify(err)));
};

// const navigation = {
//     init: function () {
//         const navigation = document.querySelector(".navbar-nav");
//         navigation.addEventListener("click", (evt) => {
//            console.log(evt);
//         });
//     }
// };
//
// document.addEventListener("DOMContentLoaded", () => {
//     navigation.init();
// });