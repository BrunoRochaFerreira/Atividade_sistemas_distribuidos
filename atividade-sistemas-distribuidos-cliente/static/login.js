document.querySelector("#form-login").addEventListener("submit", (event) => {
    event.preventDefault()
    const email = document.querySelector("#email")
    const senha = document.querySelector("#senha")

    axios.post("http://localhost:8080/usuarios/login", {
        email: email.value,
        senha: senha.value
    })
    .then(() => {
        const messagesElement = document.querySelector(".error-messages")
        messagesElement.innerHTML = ''

        localStorage.setItem('email', JSON.stringify({email: email.value}))

        window.location.href="index.html"
    })
    .catch((error) => {
        mostrarMensagem(error.response.data)
    })
})

function mostrarMensagem(mensagem) {
    const errorParagraph = document.createElement('p')
    errorParagraph.innerText = mensagem

    const messagesElement = document.querySelector(".error-messages")
    messagesElement.innerHTML = ''
    messagesElement.appendChild(errorParagraph)
}

document.querySelector("#btn-cadastro").addEventListener("click", (event) => {
    event.preventDefault()
    window.location.href="cadastro.html"
})