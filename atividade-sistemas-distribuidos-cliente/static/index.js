const objeto = localStorage.getItem('email')

let emailAutenticado = ''

// Verificar se usuário autenticado
if (objeto) {
    emailAutenticado = JSON.parse(objeto).email

    const usuarioAutenticoElemento = document.querySelector('.usuario-autenticado')
    usuarioAutenticoElemento.innerHTML = emailAutenticado
} else {
    window.location.href = 'login.html'
}

// Adicionar evento para enviar mensagem
document.querySelector('#form-mensagem').addEventListener('submit', (event) => {
    event.preventDefault()
    const email = document.querySelector('#email')
    const assunto = document.querySelector('#assunto')
    const conteudo = document.querySelector('#conteudo')

    axios.post('http://localhost:8080/mensagens', {
        emissor: {
            email: emailAutenticado,
        },
        receptor: {
            email: email.value
        },
        assunto: assunto.value,
        conteudo: conteudo.value
    })
    .then(() => {
        mostrarMensagem('Mensagem Enviada com sucesso!', 'success')
        email.value = ''
        assunto.value = ''
        conteudo.value = ''
    })
    .catch((error) => {
        mostrarMensagem(error.response.data, 'error')
    })
})

// buscar mensagens recebidas
document.querySelector('#profile-tab').addEventListener('click', async () => {
    const {data} = await axios.get(`http://localhost:8080/mensagens/recebidas/${emailAutenticado}`)
    preencherTabela('recebidas', data)
})

// buscar mensagens enviadas
document.querySelector('#contact-tab').addEventListener('click', async () => {
    const {data} = await axios.get(`http://localhost:8080/mensagens/enviadas/${emailAutenticado}`)
    preencherTabela('enviadas', data)
})

function mostrarMensagem(mensagem, type) {
    const errorParagraph = document.createElement('p')
    errorParagraph.innerText = mensagem

    const activeClass = type == 'error' ? 'text-danger': 'text-success'
    const deactiveClass = type == 'error' ? 'text-success': 'text-danger'

    const messagesElement = document.querySelector('.messages')
    messagesElement.innerHTML = ''
    messagesElement.classList.add(activeClass)
    messagesElement.classList.remove(deactiveClass)
    messagesElement.appendChild(errorParagraph)
}

function preencherTabela(type, conteudo) {
    let tableClass = ''
    let usuarioPropriedade = ''

    if (type == 'recebidas') {
        tableClass = '.mensagens-recebidas-conteudo'
        usuarioPropriedade = 'emissor'
    } else {
        tableClass = '.mensagens-enviadas-conteudo'
        usuarioPropriedade = 'receptor'
    }

    const table = document.querySelector(tableClass)
    table.innerHTML = ''

    for (let mensagem of conteudo) {
        const trElement = document.createElement('tr')

        const usuario = mensagem[usuarioPropriedade].email
        const assunto = mensagem.assunto
        const conteudo = mensagem.conteudo

        const usuarioElement = document.createElement('td')
        usuarioElement.innerHTML = usuario

        const assuntoElement = document.createElement('td')
        assuntoElement.classList.add('text-truncate')
        assuntoElement.innerHTML = assunto

        const conteudoElement = document.createElement('td')
        conteudoElement.classList.add('text-truncate')
        conteudoElement.innerHTML = conteudo

        trElement.appendChild(usuarioElement)
        trElement.appendChild(assuntoElement)
        trElement.appendChild(conteudoElement)

        table.appendChild(trElement)
    }
}

// Desloar do sistema
document.querySelector('.btn-sair').addEventListener('click', () => {
    localStorage.removeItem('email')
    window.location.href = 'login.html'
})