---
layout: page
title: Zhang Xin Yue's Project Portfolio Page
---

## Project: MyMods

MyMods is a desktop mod tracking application used for tracking mods in NUS.
The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the Progress command.
  * What it does: Allows the user to track their progress towards their target CAP by calculating and displaying the average CAP required for their remaining modules.
  * Justification: This feature helps the user to set a clear goal for their remaining modules and keeps them informed about whether their target CAP is achievable.
  * Highlights: This enhancement works together with our other new feature: the ability to set a goal or target CAP, and it required an in-depth analysis of design alternatives. A side feature to calculate the number of MCs taken was added to aid the implementation of this command.

* **New Feature**: Added the functionality to change semesters to Update command.
  * What it does: Allows the user to shift a module to a different semester.
  * Justification: This feature allows the user to change the semester of a module directly without having to delete it and add it in again.
  * Highlights: This enhancement affects existing features and commands that are related to semester and features to be added in the future for the update command, hence it required an in-depth analysis of design alternatives.

* **Enhancements to existing features**:
  * Renamed Edit to Update and allows user to delete the grade of a module by leaving the grade parameter out when updating a module. (Pull requests [\#54](https://github.com/AY2021S1-CS2103T-T17-1/tp/pull/54), [\#119](https://github.com/AY2021S1-CS2103T-T17-1/tp/pull/119))
  * Modified CAP display to ensure it reflects the CAP correctly whenever changes are made to the module list. (Pull requests [\#61](https://github.com/AY2021S1-CS2103T-T17-1/tp/pull/61))
  * Updated the UI to display the semester for each module. (Pull requests [\#132](https://github.com/AY2021S1-CS2103T-T17-1/tp/pull/132))
  * Renamed AddressBook to GradeBook. (Pull requests [\#221](https://github.com/AY2021S1-CS2103T-T17-1/tp/pull/221))

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=xyzhangg&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=xyzhangg&tabRepo=AY2021S1-CS2103T-T17-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code)

* **Documentation**:
  * User Guide:
    * Added documentation for the features `progress`, `help` and `exit`(No PR by me as we typed collectively in Google Docs and pushed by one person)
  * Developer Guide:
    * Added implementation details of the `progress` feature. (Pull requests [\#93](https://github.com/AY2021S1-CS2103T-T17-1/tp/pull/93), [\#257](https://github.com/AY2021S1-CS2103T-T17-1/tp/pull/257))
    * Added sequence diagram for Logic component (Pull requests [\#257](https://github.com/AY2021S1-CS2103T-T17-1/tp/pull/257))

* **Community**:
  * PRs reviewed: 29
  * Reported bugs and suggestions for other teams during PE-D: 7 issues
