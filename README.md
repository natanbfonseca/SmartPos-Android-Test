# Avaliação Técnica - Teste de Display para Smart POS

![Demonstração do App](https://github.com/natanbfonseca/SmartPos-Android-Test/blob/66b15dcfd18bb47fdf838499757a9b02dedc8fa1/docs/images/demo.gif)

## 📖 Sobre o Projeto

Este projeto é uma solução para uma avaliação técnica que consiste no desenvolvimento de um aplicativo de teste de display para um terminal Smart POS. O objetivo principal é validar o funcionamento de todos os pontos da tela touch por meio de um teste interativo, seguindo as melhores práticas de desenvolvimento Android com uma arquitetura moderna e robusta.

O aplicativo desafia o usuário a "pintar" uma grade que cobre toda a tela. O teste é considerado bem-sucedido apenas se todas as células da grade forem ativadas dentro de um tempo limite de 10 segundos.

## ✨ Funcionalidades

- **Teste de Grade Interativo:** O usuário deve tocar ou deslizar o dedo sobre uma grade para ativar todas as suas células.
- **Feedback Visual Imediato:** As células da grade mudam de cor (para verde) assim que são tocadas.
- **Validação Completa:** Um botão de "Passou" só é habilitado quando 100% da grade é ativada.
- **Timeout de 10s:** Se o teste não for concluído em 10 segundos, ele é automaticamente considerado uma falha, garantindo a agilidade do operador.
- **Notificações Claras:** Toasts informam o usuário sobre o sucesso ou a falha do teste.

## 🛠️ Tecnologias e Arquitetura

O projeto foi construído com foco na qualidade, manutenibilidade e testabilidade do código, utilizando um stack tecnológico moderno e seguindo princípios de arquitetura de software consolidados.

- **Linguagem:** **Kotlin** como linguagem principal, aproveitando seus recursos de segurança e concisão.
- **Interface (UI):** **Jetpack Compose** para a construção de uma UI declarativa, moderna e reativa.
- **Arquitetura:** **Clean Architecture** combinada com o padrão **MVVM**. Essa escolha garante uma separação clara de responsabilidades, facilitando testes e futuras manutenções.
- **Injeção de Dependência:** **Koin** para gerenciar o ciclo de vida das dependências de forma simples e pragmática.
- **Assincronismo:** **Kotlin Coroutines & Flow** para gerenciar as operações assíncronas, como o timer do teste e as atualizações de estado da UI, de forma eficiente e estruturada.

### Estrutura das Pastas

A Clean Architecture foi implementada com a seguinte separação de módulos:

- `:presentation` (Camada de **Presentation**): Contém as telas (Composables), ViewModels, navegação e a configuração do Koin.
- `:domain` (Camada de **Domínio**): O coração da aplicação. Contém os UseCases (lógica de negócio) e os modelos de dados puros, sem nenhuma dependência do Android.
- `:data` (Camada de **Dados**): Implementa as interfaces (Repositórios) definidas no domínio e gerencia a fonte de dados (neste caso, o estado do teste em memória).

## 🚀 Como Executar

1.  Clone o repositório:
    ```bash
    git clone [https://github.com/natanbfonseca/SmartPos-Android-Test.git](https://github.com/natanbfonseca/SmartPos-Android-Test.git)
    ```
2.  Abra o projeto no Android Studio (versão Flamingo ou mais recente recomendada).
3.  Aguarde o Gradle sincronizar as dependências.
4.  Compile e execute em um emulador ou dispositivo físico Android.

## 🧠 Desafios e Aprendizados

Durante o desenvolvimento, o principal desafio foi garantir a precisão e a performance da detecção de toque na grade, especialmente em gestos de deslize rápidos.

- **Problema Inicial:** Os toques eram registrados com um deslocamento vertical (offset), e gestos rápidos "pulavam" células.
- **Solução:** A solução final envolveu uma investigação profunda do sistema de layout e de eventos de ponteiro do Jetpack Compose. A correção definitiva foi alcançada ao remover todos os modificadores de tamanho (`.weight` e `.fillMaxSize`) do `Box` que envolve a grade. Isso forçou um **acoplamento perfeito** entre a área de detecção de gestos e a área visual da grade, garantindo que o `size` usado para os cálculos de toque fosse idêntico ao tamanho real da UI. Além disso, foi implementada uma **lógica de interpolação de caminho** para que, mesmo em um deslize rápido, todas as células no trajeto do dedo sejam ativadas.

Este processo reforçou a importância de compreender as nuances das fases de medição e posicionamento do Jetpack Compose para criar interações complexas e performáticas.
