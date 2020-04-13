**Настройка Gitlab CI для сборки проекта и деплоя модульного приложения на HEROKU**

**Введение в Gitlab CI**

Gitlab CI - это инструмент непрерывной интеграции(CI), который поставляется вместе с Gitlab из коробки.
В отличии от внешних CI систем( например _Jenkins_ ), Gitlab CI интегрирован в Gitlab, что позволяет упростить 
работу при настройке CI системы( Например: аутентификация/авторизация пользователя, доступ к исходникам и т.д).

Gitlab CI работает с такими понятиеми как _Pipeline_, _Job_, _Stage_

_Pipeline_ - это компонент верхнего уровня, который отвечает за непрервыную интеграцию.
Он состоит из таких элементов, как _Stages_.

_Stage_ - представляет из себя этап, который описывает определенный набор последовательных действий.
В рамках одного пайпа все этапы выполняются последовательно. (Например: этап тестирования, этап сборки билда и т.д )

_Job_ - представляет из себя задачу, которая выполняет весь набор описанных в рамках этапа действий 

Gitlab CI выполняет jobs с помощью Gitlab-Runner. 
Gitlab-Runner - это проект, выполняющий заданную через GitLab CI задачу и отправляет резльтаты обратно на GitLab.
Можно использовать один общий Gitlab-Runner для нескольких проектов, можно для каждого отдельного проекта настраивать свой Gitlab-Runner.
Ниже будут описаны шаги по настройке gitlab-runner на отдельной Linux машине с ОС (Centos 7)

Для того, чтобы настроить CI для проекта необходимо описать pipeline в рамках файла: .gitlab-ci.yml, который
необходимо поместить в корневую директорию проекта.

Итого: 
Для настройки Gitlab CI для сборки проекта и последующего деплоя нужно выполнить следующие шаги:
1) Создание pipeline, а именно создание файла: .gitlab-ci.yml в корневой директории проекта
2) Настройка и конфигурация Gitlab-Runner

**Создание pipeline, а именно создание файла: .gitlab-ci.yml в корневой директории проекта** 

В рамках данной части я продемонстрирую пример pipeline, который был создан для проекта heroku-demo.
В рамках данного pipeline описаны 4 этапа: 
- package 
- create docker images
- push to gitlab registry
- push to heroku registry, release

Первоначально я продемонстрирую весь файл целиком для наглядности, далее я буду разбирать его по кусочкам:

#####################################################################
`before_script:
- sudo chmod -R 777 ./

stages:
- package
- create docker images
- push to gitlab registry
- push to heroku registry, release

package:
  stage: package
  script:
    - sudo docker run --rm -v ${PWD}:/usr/src/mymaven -v ${HOME}/.m2:/root/.m2 -w /usr/src/mymaven maven:3.6.3-jdk-11-slim  mvn -Pproduction clean package
  artifacts:
   expire_in: 1 days
   paths:
    - heroku-web/target/*.jar
    - heroku-worker/target/*.jar

create docker images:
  stage: create docker images
  script:
  - sudo docker build -f heroku-web/Dockerfile -t registry.vrpconsulting.com/konstantin.bondarchuk/heroku-demo/heroku-web -t registry.heroku.com/java-heroku-demo-1703/web  .
  - sudo docker build -f heroku-worker/Dockerfile -t registry.vrpconsulting.com/konstantin.bondarchuk/heroku-demo/heroku-worker -t registry.heroku.com/java-heroku-demo-1703/worker .

push to gitlab registry:
  stage: push to gitlab registry
  script:
    - sudo docker login -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD registry.vrpconsulting.com
    - sudo docker push registry.vrpconsulting.com/konstantin.bondarchuk/heroku-demo/heroku-web
    - sudo docker push registry.vrpconsulting.com/konstantin.bondarchuk/heroku-demo/heroku-worker

push to heroku registry, release:
  stage: push to heroku registry, release
  script:
    - sudo docker login -u _ -p $HEROKU_PRODUCTION_API_KEY registry.heroku.com
    - sudo docker push registry.heroku.com/java-heroku-demo-1703/web
    - sudo docker push registry.heroku.com/java-heroku-demo-1703/worker
    - sudo docker run --rm -e HEROKU_API_KEY=$HEROKU_PRODUCTION_API_KEY wingrunr21/alpine-heroku-cli container:release web worker --app java-heroku-demo-1703

after_script:
- sudo chmod -R 777 ./`
#####################################################################

1) Часть №1
before_script:
- sudo chmod -R 777 ./

В рамках данной части говорится о том, что перед началом выполнения каждого из скриптов описанных на каждом этапе всем файлам 
в рамках директории "./" будут выданы права 777(пользователь может читать, редактировать и запускать на выполнение)

2) Часть №2
stages:
- package
- create docker images
- push to gitlab registry
- push to heroku registry, release

В рамках данной части указывается набор всех этапов, которые включает в себя текущий пайп

3) Часть №3
package:
  stage: package
  script:
    - sudo docker run --rm -v ${PWD}:/usr/src/mymaven -v ${HOME}/.m2:/root/.m2 -w /usr/src/mymaven maven:3.6.3-jdk-11-slim  mvn -Pproduction clean package
  artifacts:
   expire_in: 1 days
   paths:
    - heroku-web/target/*.jar
    - heroku-worker/target/*.jar
    
В рамках данной части описывается этап: package.
В рамках данного этапа можно обратить внимание на следующие вещи:

3.1) Часть №3.1
script:
    - sudo docker run --rm -v ${PWD}:/usr/src/mymaven -v ${HOME}/.m2:/root/.m2 -w /usr/src/mymaven maven:3.6.3-jdk-11-slim  mvn -Pproduction clean package

В рамках данного кусочка производится сборка maven проекта в рамках докер контейнера.
--rm - флаг, отвечающий за удаление всех системных файлов докер контейнера после его уничтожения
-w /usr/src/mymaven - каталог в рамках докер контейнера где будет производтся сборка maven проекта
-v ${PWD}:/usr/src/mymaven - указывается VOLUME на то, что все файлы текущего каталога ретранслируются в каталог /usr/src/mymaven докер контейнера
-v ${HOME}/.m2:/root/.m2 - указывается VOLUME на то, что все файлы из каталога ${HOME}/.m2 ретранслируются в каталог /root/.m2. 
Это выполнено специально, чтобы при сборке maven проекта мы не скачивали зависимости из удаленного репозитория, а брали их из локального репозитория( Большая экономия времени выполнения на данном этапе )
- maven:3.6.3-jdk-11-slim - докер image, с помощью которого мы запускаем докер контейнер и производим внитри него сборку проекта
- mvn -Pproduction clean package - команда, которая выполняет билд проекта в рамках докер контейнера

3.2) Часть №3.2
artifacts:
   expire_in: 1 days
   paths:
    - heroku-web/target/*.jar
    - heroku-worker/target/*.jar
    
В рамках данного кусочка мы указываем, что хотим хранить артефакты сборки в течении одного дня после выполнения данного этапа.
Это необходимо делать, т.к. при каждом запуске нового этапа будет полностью удаляться сборочная директория.  

4) Часть №4  
create docker images:
  stage: create docker images
  script:
  - sudo docker build -f heroku-web/Dockerfile -t registry.vrpconsulting.com/konstantin.bondarchuk/heroku-demo/heroku-web -t registry.heroku.com/java-heroku-demo-1703/web  .
  - sudo docker build -f heroku-worker/Dockerfile -t registry.vrpconsulting.com/konstantin.bondarchuk/heroku-demo/heroku-worker -t registry.heroku.com/java-heroku-demo-1703/worker .

В рамках данной части описывается этап: create docker images.
В нашем примере у нас есть модульное приложение(heroku-demo), которое состоит из 2 модулей: heroku-web и heroku-worker
Соответсвенно в рамках данного этапа мы создаем 2 docker images, каждый содержит по 2 тэга:
registry.vrpconsulting.com/konstantin.bondarchuk/heroku-demo/heroku-web -  данный тэг необходим для дальнейшего пуша image в gitlab-registry
registry.heroku.com/java-heroku-demo-1703/web - данный тэг необходим для дельнейшего пуша image в heroku-registry

5) Часть №5
push to gitlab registry:
  stage: push to gitlab registry
  script:
    - sudo docker login -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD registry.vrpconsulting.com
    - sudo docker push registry.vrpconsulting.com/konstantin.bondarchuk/heroku-demo/heroku-web
    - sudo docker push registry.vrpconsulting.com/konstantin.bondarchuk/heroku-demo/heroku-worker
    
В рамках данной части описывается этап: push to gitlab registry.
В рамках данного этапа можно обратить внимание на следующие вещи:

5.1) Часть №5.1
- sudo docker login -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD registry.vrpconsulting.com

В рамках данного кусочка мы производим авторизацию в gitlab registry( В нашем случае: registry.vrpconsulting.com )
Переменные CI_REGISTRY_USER и CI_REGISTRY_PASSWORD предоставляются автоматически с помощью gitlab, т.е. их нам не нужно нигде в настройках указывать дополнительно

5.2) Часть №5.2
- sudo docker push registry.vrpconsulting.com/konstantin.bondarchuk/heroku-demo/heroku-web
- sudo docker push registry.vrpconsulting.com/konstantin.bondarchuk/heroku-demo/heroku-worker

В рамках данного кусочка мы пушим выполненные на предыдущем этапе докер images в gitlab-registry


6) Часть №6
push to heroku registry, release:
  stage: push to heroku registry, release
  script:
    - sudo docker login -u _ -p $HEROKU_PRODUCTION_API_KEY registry.heroku.com
    - sudo docker push registry.heroku.com/java-heroku-demo-1703/web
    - sudo docker push registry.heroku.com/java-heroku-demo-1703/worker
    - sudo docker run --rm -e HEROKU_API_KEY=$HEROKU_PRODUCTION_API_KEY wingrunr21/alpine-heroku-cli container:release web worker --app java-heroku-demo-1703
    
В рамках данной части описывается этап: push to heroku registry, release.
В рамках данного этапа можно обратить внимание на следующие вещи:

6.1) Часть №6.1
- sudo docker login -u _ -p $HEROKU_PRODUCTION_API_KEY registry.heroku.com
В рамках данного кусочка мы производим авторизацию в heroku registry( registry.heroku.com )
Переменная HEROKU_PRODUCTION_API_KEY может иметь любое название, 
значение же данной переменной генерируется с помощью HEROKU CLI команды(heroku auth:token) на уже авторизовавшемся пользователе.
Сама же переменная должна хранится в gitlab, а именно( "ProjectName"->Settings->CI/CD->Variables )

6.2) Часть №6.2
 - sudo docker push registry.heroku.com/java-heroku-demo-1703/web
 - sudo docker push registry.heroku.com/java-heroku-demo-1703/worker
 
 В рамках данного кусочка мы пушим выполненные на предыдущем этапе докер images в heroku-registry
 
6.3) Часть №6.3
  - sudo docker run --rm -e HEROKU_API_KEY=$HEROKU_PRODUCTION_API_KEY wingrunr21/alpine-heroku-cli container:release web worker --app java-heroku-demo-1703
  
С помощью данного кусочка мы релизим наши изменения на HEROKU:
- -e HEROKU_API_KEY=$HEROKU_PRODUCTION_API_KEY - указываем env, с нашим HEROKU_API_KEY
- wingrunr21/alpine-heroku-cli - докер image, с помощью которого мы запускаем докер контейнер и производим внитри него необходимые вызовы heroku CLI
- container:release web worker --app java-heroku-demo-1703 - команда, для HEROKU CLI, которая отвечает за релиз в рамках приложения java-heroku-demo-1703 сервисов с process-type: web и worker


P.S. Так выглядят Dockerfile для heroku-web и heroku-worker:

1) /heroku-web/Dockerfile

#####################################################################
FROM appinair/jdk11-maven:latest
MAINTAINER Konstantin Bandarchyk <kbandarhyk@gmail.com>

ADD heroku-web/target/heroku-web-1.0-SNAPSHOT.jar /home/app/heroku-web-1.0-SNAPSHOT.jar
WORKDIR /home/app

ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-jar", "heroku-web-1.0-SNAPSHOT.jar" ]

EXPOSE 8080
#####################################################################

2) /heroku-worker/Dockerfile

#####################################################################
FROM appinair/jdk11-maven:latest
MAINTAINER Konstantin Bandarchyk <kbandarhyk@gmail.com>

ADD heroku-worker/target/heroku-worker-1.0-SNAPSHOT.jar /home/app/heroku-worker-1.0-SNAPSHOT.jar
WORKDIR /home/app

ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-jar", "heroku-worker-1.0-SNAPSHOT.jar" ]

EXPOSE 8082
#####################################################################
