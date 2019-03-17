# SimpleSNS

## Documentation

We started this `open source` project from 2019.01.13 at WeWork, Gangnam, Seoul.

Our goal was each and everyone has the ability to develop a simple(just core functions) social network android application and learning working together.
And also learning how to set AWS, MariaDB, technics of Java and its improvement.	
	
### Environment ###

1. Client(Android App)
    - Android Studio 3.2 & 3.3
    - minSdk: 19
    - targetSdk: 28
    - AppName: Simple SNS
    - Fundamental Package: org.simplesns
    - [Convention(HEYDEALER)](https://github.com/PRNDcompany/android-style-guide)

2. Server
    - OS: Ubuntu
    - Cloud Service: AWS(IaaS)
    - Server: Node.js
    - Database : MariaDB 
    
### Configuration ###

First of all, you need to clone or download this repository in zip file.
For clone in termidal,

    git clone https://github.com/chanjungkim/simpleSNS.git

Now you are ready for the nexts.

#### Node.js ####

Apprently, `Node.js` folder has the Node.js project file for server side.
Once you installed `npm`, then you can simply start this project by `npm start` in `Node.js/SimpleSNS` folder.

For more details, go to [nodejs](./Node.js)

#### Android ####

For Android Studio project file, open `Android/SimpleSNS` folder on Android Studio.

For more details, go to [android](./Android)

#### DB ####
    
This folder doesn't have any DBMSs. However, it contains the files that you can apply in your DBMS such as creating tables, inserting records or something.

For more details, go to [db](./DB)

### Our channel(Discord) ###

https://discord.gg/JV9Wk6R

## Git Management

### Environment

Tool: [SourceTree](https://www.sourcetreeapp.com/)

Method: Git Flow

#### Why Git Flow?

1. Less conflictions & confusions between members while working on a team project.
2. Easy to check the history in the future.
3. Each branches have roles and the developers can be in charge of one branch and take responsibility.
4. Productive way for Version Controlling and fast appropriate actions for users' feedback.

### 용어 설명

- fetch: 서버에서 상태를 가져옴.
- pull: 서버에서 가져옴.
- stage: 서버에 보낼 파일들을 준비함.
- commit: 서버에 보낼 준비함.
- push: 서버로 보냄.
- stash: 미완료된 작업을 숨겨둠.
- branch: 작업을 따로 함.
- merge: 브런치를 합침.
- rebase: 히스토리를 다시 시작해서 브런치를 합침.
- fork: 자신의 repository로 가져옴.
- Pull Request: 브런치에 합치기 위해 요청을 보냄.
- tag: 버전을 넣을 때 사용됨.

### How does Git Flow work?

- features: 기능들의 브랜치들.
- development: 개발 중인 프로젝트의 큰 줄기. features들이 여기에 merge되며, release로 넘어가는데, QA가 끝나고 release 브랜치와 합쳐지기도 한다.
- release: master로 보내기 전에 버그 수정 등 QA를 하는 브랜치. develop과 master 둘 다 적용시켜준다.
- hotfixes: master에서 버그 등이 발생했을 때 잠시 만들어 버그 수정을 하고 master에 다시 합치는 작은 브랜치.
- master: 프로젝트 최종본. merge 때마다 tag를 달아 버전을 하나씩 올리게 됨.

<image src="http://woowabros.github.io/img/2017-10-30/git-flow_overall_graph.png" style="width:40%; height:auto;"/>
