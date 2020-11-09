---
layout: page
title: Augustine Kau's Project Portfolio Page
---

## Project: MyMods

MyMods is a desktop app for tracking modules and grades, optimized for use for students who prefer typing via a
Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).

With MyMods, you are able to keep track of your module results efficiently, easily make S/U decisions,
and view your academic progress.
It is written in Java.

Given below are my contributions to the project.

* **New Feature**: Added the ability for users to set their goal according to the university's Honours Classification System
  * What it does: Allow the user to set their goals or view the list of goal available.
  * Justification: This feature is needed for recommendSU and Progress report feature as it needs to take into account the user's desired goal.
  * Highlights: This enhancement affects how the application saves and read from the json file. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to how the file is saved and read.

* **New Feature**: Added the ability for MyMods to recommend modules to S/U for the user.
  * What it does: Recommend S/U looks through the all the modules by the user and cross check with our data file on where the module can be S/U by NUS restrictions. It then compares that module's grade with the user's defined goal.
  * Justification: This feature gives the user more purpose of using MyMods as it is a personalised list and it is useful as students (esp NUS freshmen) have difficulty deciding what modules they should S/U.
  * Highlights: This enhancement affects existing commands (semester feature) and commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required consideration of OOP design principles.

 * **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=augustinekau&tabRepo=AY2021S1-CS2103T-T17-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code)

* **Enhancements to existing features**:
    * Updated and refactored the code from the original AB3 code to fit MyMods functionality (Pull requests [\#46](https://github.com/AY2021S1-CS2103T-T17-1/tp/pull/46), [\#47](https://github.com/AY2021S1-CS2103T-T17-1/tp/pull/47))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `Set Goal`, `Recommend S/U` and `Find` (No PR by me as we typed collectively in Google Docs and pushed by one person)
    * Added documentation for Product Overview `Table of Contents`, `About`, `Glossaries` and `Getting Started` (No PR by me as we typed collectively in Google Docs and pushed by one person)
    * Did cosmetic tweaks to existing documentation by including tips and all screenshot annotation (No PR by me as we typed collectively in Google Docs and pushed by one person)
  * Developer Guide:
    * Added implementation details of the `recommendSU` feature.
    * Edited sections with diagrams on Architecture and Storage components under Design.

* **Community**:
  * Number of PRs reviewed (with trivial review comments): 58
  * Reported 13 bugs and suggestions for other teams in PE-D
