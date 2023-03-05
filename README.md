feature_subscriptions pull request: https://github.com/pavbatol/java-explore-with-me/pull/4

# java-explore-with-me

#### (Java-разработчик - дипломный проект )

### Что это

Серверная часть приложения. Сервис представляет собой афишу. В этой афише можно предложить какое-либо событие
от выставки до похода в кино и собрать компанию для участия в нём.

![ewm-face.png](resource%2Fewm-face.png)

### Возможности

- #### Основной сервис (ewm-main)
    - Зоны доступа: public, private, admin
    - Создания мероприятия: дата, кол-во участников, платность, категория, место проведения и др.
    - Заявка на участие в событии: одобрена/отклонена инициатором события
    - Просмотр событий и поиск по глубокому фильтру одним запросом:
        - инициатор
        - состояние события: PENDING, PUBLISHED, CANCELED
        - категория
        - диапазон дат
        - содержит текст в аннотации или описании
        - платность
        - Доступность записи на участие
        - Сортировка по дате или по кол-ву просмотров
        - Постраничный вывод
    - Админ:  полный контроль над пользователями и категориями, может редактировать/опубликовать/отклонить событие,
      составлять и публиковать подборки событий
    - Пользователь: может создавать/редактировать/просматривать события, подтверждать/отклонять заявки на участие,
      создавать заявки на участие в событиях других пользователей.

    - Пользователь: **`Дополнительная функциональность:`** подписываться на других пользователей. Одним
      запросом можно подписаться на несколько пользователей, так же редактировать одним запросом весь список. Просматривать
      события отдельным списком на кого подписан. При просмотре есть возможность использовать фильтр. Каждый может
      разрешить/запретить подписку на себя.

    - Незарегистрированный: просматривать события
    - Событие получает от сервиса статистики кол-во просмотров уникальные/нет
- #### Сервис статистики (ewm-stats)
    - Реализован как отдельное приложение и может использоваться для любых других сервисов. Имеет в своем составе
      готового клиента, предназначенного для обращения к самому себе. Просто подключите как зависимость.
    - Принимает в контроллере и фиксирует в БД:
        - Имя сервиса приславшего запрос на сохранение
        - end-point
        - IP-адрес
        - время обращения к end-point
    - Можно обращаться к этому сервису передав список из end-point. В ответе получите список статистики по каждому, если
      он есть в БД.

### Схемы

#### Основной сервис (ewm-main):

![er-diagram-ewm-main.png](resource%2Fer-diagram-ewm-main.png)

#### Сервис статистики (ewm-stats):

![er-diagram-ewm-stats.png](resource%2Fer-diagram-ewm-stats.png)

### Инструментарий

- SpringBoot
- JPA, HIBERNATE
- postgresql
- Maven
- Docker-compose

### АПИ

- [ewm-main-service-spec.json](ewm-main-service-spec.json)
- [ewm-stats-service-spec.json](ewm-stats-service-spec.json)
- SwaggerAPI:
    - ewm-main: http://localhost:8080/swagger-ui.html
    - ewm-stats: http://localhost:9090/swagger-ui.html
- Доступно через actuator

### Запуск

- Собрать проект командой: mvn clean package
- Запустить из каталога проекта под правами администратора:
    - docker-compose build
    - docker-compose up
