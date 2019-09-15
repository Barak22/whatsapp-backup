// (async () => {
//   const response = await fetch("http://localhost:8080/conversation");
//   const json = await
//     response.json();
//
//   Object.keys(json.messages).forEach(k => {
//     createDateComponent(k);
//     json.messages[k].forEach(createMessageComponent)
//   });
// })();
//
// function createMessageComponent(message) {
//   const date = new Date(message.date);
//   document.querySelector('.col-8').innerHTML += `<div>${date.getHours()}:${date.getMinutes()} : ${message.from} : ${message.content}</div>`
// }
//
// createDateComponent = (date) => {
//   document.querySelector('.col-8').innerHTML += `<div>${date}</div>`
// };
//

// function uploadButtonOnClick() {
//   document.getElementById('input-file').click()
// }

function inputFileOnChange(file) {
  const path = document.getElementById('input-file').files[0];
  const reader = new FileReader();
  console.log(path);
  reader.readAsBinaryString(path);
  reader.onloadend = e => {
    console.log(e.target.result); // TODO: Send the zip file to the scala server
  }

}
