document.querySelector("#form-cadastro").addEventListener("submit", (event) => {
    event.preventDefault()
    const nome = document.querySelector("#nome")
    const email = document.querySelector("#email")
    const senha = document.querySelector("#senha")

    axios.post("http://localhost:8080/usuarios", {
        nome: nome.value,
        email: email.value,
        senha: senha.value
    })
    .then(() => {
        mostrarMensagem('Usuário Cadastrado com sucesso!', 'success')
        nome.value = ''
        email.value = ''
        senha.value = ''
    })
    .catch((error) => {
        mostrarMensagem(error.response.data, 'error')
    })
})

function mostrarMensagem(mensagem, type) {
    const errorParagraph = document.createElement('p')
    errorParagraph.innerText = mensagem

    const activeClass = type == 'error' ? 'text-danger': 'text-success'
    const deactiveClass = type == 'error' ? 'text-success': 'text-danger'

    const messagesElement = document.querySelector(".messages")
    messagesElement.innerHTML = ''
    messagesElement.classList.add(activeClass)
    messagesElement.classList.remove(deactiveClass)
    messagesElement.appendChild(errorParagraph)
}

document.querySelector("#btn-login").addEventListener("click", (event) => {
    event.preventDefault()
    window.location.href="login.html"
})