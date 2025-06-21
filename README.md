# 📱 MoveOn – Aplicativo de Treinos Personalizados

**MoveOn** é um app Android completo para quem busca praticidade e controle na organização dos treinos de academia. Com uma interface moderna, o app permite criar treinos personalizados, acompanhar o progresso em um calendário e registrar anotações — tudo vinculado a perfis de usuário.

---

## 🚀 Funcionalidades

- 👤 **Perfis Personalizados**: Crie múltiplos perfis com nome e imagem, ideal para quem compartilha o app com familiares ou amigos.
- 🏋️ **Gerenciamento de Treinos**: Adicione treinos por grupo muscular (Peito, Perna, Braço, etc.) com exercícios personalizados.
- ⏱️ **Execução de Exercícios**: Acompanhe séries, repetições, tempo de descanso e registre o progresso.
- 📆 **Calendário de Treinos**: Veja os dias com treinos concluídos em destaque no calendário.
- 📝 **Anotações**: Registre metas, observações ou lembretes de treino.
- 📊 **Histórico de Treinos**: Acompanhe os treinos executados por perfil e treino.
- 🌗 **Modo Claro/Escuro**: Interface adaptável com Material Design.

---

## 🛠️ Tecnologias Utilizadas

- **Java** – Linguagem principal
- **Android Studio** – Ambiente de desenvolvimento
- **SQLite** – Banco de dados local
- **SharedPreferences** – Armazenamento leve de dados
- **Material Components** – Design moderno com suporte ao tema escuro

---

## 📸 Capturas de Tela

> *(Adicione aqui prints reais do app rodando: tela de treinos, perfil, calendário, execução de treino, etc.)*

---

## 🏗️ Estrutura do Projeto

```plaintext
├── MainActivity.java
├── PerfilActivity.java
├── TreinosActivity.java
├── AdicionarTreinoActivity.java
├── TreinoExecucaoActivity.java
├── NotasActivity.java
├── adapters/
│   └── TreinoAdapter.java
│   └── PerfilAdapter.java
├── models/
│   └── Treino.java
│   └── Exercicio.java
│   └── Execucao.java
├── database/
│   └── TreinoDBHelper.java
│   └── ExecucaoDBHelper.java
└── res/
    └── layout/
    └── drawable/
    └── values/
📦 Instalação e Execução
Clone o repositório:

bash
Copiar
Editar
git clone https://github.com/seu-usuario/moveon.git
Abra no Android Studio.

Compile e execute em um dispositivo com API 27 (Android 8.1) ou superior.

Permita acesso à galeria (caso use upload de imagem nos perfis/treinos).

🤝 Contribuições
Contribuições são bem-vindas! Se quiser propor melhorias ou correções:

Faça um fork do projeto

Crie uma branch (git checkout -b feature/sua-funcionalidade)

Faça o commit (git commit -m 'Adiciona nova funcionalidade')

Dê push na branch (git push origin feature/sua-funcionalidade)

Abra um Pull Request

📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

📬 Contato
Desenvolvido por Luís Henrique Gomes Fagundes
📧 Email: fagundesgluis@gmail.com
🔗 LinkedIn: https://www.linkedin.com/in/fagunds/
