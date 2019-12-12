const urlParams = new URLSearchParams(window.location.search);

if(localStorage.getItem('token')){
    sendToken(localStorage.getItem('token'));
}
else if(urlParams.has('code')){
    const url = `http://localhost:8080/TheFinalScore-Backend/api/token?code=${urlParams.get('code')}`;
    urlParams.delete('code');

    fetch(url)
    .then(response => response.text())
    .then(token => localStorage.setItem('token', token))
    .then(() => sendToken(localStorage.getItem('token')))
    .then(() => clearQueryParams());
}

function sendToken(token){
    const url = `http://localhost:8080/TheFinalScore-Backend/api/login?token=${token}`;
    fetch(url)
    .then(response => response.json())
    .then(data => alert(`Welcome ${data.login}, your id is ${data.id}`));
}

function clearQueryParams(){
    if(urlParams.has('code'))
        urlParams.delete('code');
}