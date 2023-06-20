## Relatório de Desenvolvimento - Aplicativo de E-Commerce

O código fornecido é um exemplo de um aplicativo de e-commerce em Java. O relatório a seguir descreve o processo de desenvolvimento, as decisões tomadas e as dificuldades encontradas durante a criação deste código.

### 1. Visão Geral
O aplicativo de e-commerce é implementado na classe principal `App`. Ele utiliza controladores para gerenciar as funcionalidades relacionadas a usuários, lojas, produtos e perfis. O programa permite que os usuários façam login, criem contas, naveguem pela loja, adicionem produtos ao carrinho, acessem seus perfis e concluam o checkout.

### 2. Estrutura do Código
- Existem quatro controladores utilizados: `UserController`, `ShopController`, `ProductController` e `ProfileController`. Cada controlador é responsável por lidar com um conjunto específico de funcionalidades.

### 3. Fluxo do Programa
O método `main` é o ponto de entrada do programa e chama o método `startProgram` para iniciar a execução. O método `startProgram` verifica se um usuário está logado e se é um administrador, em seguida, exibe as opções disponíveis com base nesses dados. A escolha do usuário é lida através do método `chooseAction` e, em seguida, o método correspondente `doAction` é chamado para executar a ação selecionada.

### 4. Fluxo de Execução
- Se nenhum usuário estiver logado, as opções disponíveis são "Login" e "Criar uma conta". 
- Se o usuário for um administrador, as opções adicionais de "Listar usuários", "Criar um usuário", "Remover um usuário" e "Logout" são exibidas.
- Se o usuário estiver logado, mas não for um administrador, as opções são "Ir para a loja", "Ir para meus produtos", "Ir para meu perfil", "Ir para o checkout", "Remover item do carrinho" e "Logout".

### 5. Decisões Tomadas
- A estrutura do código foi organizada em classes e controladores para melhorar a modularidade e a separação de responsabilidades.
- Foi utilizada uma abordagem recursiva para permitir que o usuário execute várias ações consecutivamente, sem ter que reiniciar o programa.
- A interação com o usuário é feita através de métodos utilitários como `ScannerUtils` e `TerminalUtils`, que facilitam a leitura de entrada e exibição de mensagens formatadas no terminal.

### 6. Dificuldades Encontradas
- Gerenciamento de estado do usuário.
- Implementação de recursos de segurança, como autenticação e autorização.
- Relacionamento entre tabelas