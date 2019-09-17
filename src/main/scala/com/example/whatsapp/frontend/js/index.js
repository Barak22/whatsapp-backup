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
  const request = document.getElementById('input-file').files[0];
  // const reader = new FileReader();
  // console.log(path);
  // reader.readAsBinaryString(path);
  // reader.onloadend = e => {
  //   const request = e.target.result; // TODO: Send the zip file to the scala server
  console.log(request)
  const xhr = new XMLHttpRequest();
  xhr.open('POST', 'http://localhost:8080/upload');
  xhr.onload = () => {
    console.log(xhr);
    if (xhr.status === 200) {
      // File(s) uploaded.
      console.log('Upload');
    } else {
      alert('An error occurred!');
    }
  };
  xhr.setRequestHeader('Content-Type', 'application/zip');
  console.log(xhr.send(request))

  // }

}
