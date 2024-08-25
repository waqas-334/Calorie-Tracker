# Calorie Tracker Multi-module Android App

[./meta-data/screen_recording.mov](Checkout the video available here)

This app is built using

- Kotlin
- Jetpack Compose
- Kotlin DSL

It incorporates clean code architecture and separation of concerns using multiple module.
It is the end result of following the multi-module architecture course by Pl-Coding, couldn't find the course on his website so can't link it.

## What I learnd

## Data Layer

- Database implementation, remote APIs, preferences, etc
- Mappers for DB entities & DTOs
  DTO is basically what we get from the API, or Kotlin representation of the JSON Response

## Domain Layer

- The innermost layer
- Contains use cases that contain business logic
  The use case is a class that does one single thing, like calculating nutrition for this specific date. Or searching for food.
- Contains Models classes
  A combined version of an entity and DTO class

## Presentation Layer

- Used to present something, like Fragments or Activities
- Also contains ViewModels
  Use of ViewModels is as a presenter, it is used to call UseCases from Domain Layer, map the output to state and UI classes will observer that state and show the data on screen.

## Advantages Multi-module App

- Clear Separation
  Say we have a module for the Presentation layer and another for the Data layer, they can not access each other so they shouldn't depend on each other.
  It’s not about just code separation, it’s also about team separation.
- Faster Gradle builds
- Support for instant apps & dynamic features
- Resuability

## Disadvantages of Multi-module app

- Lots of initial setup involved
- Not knowing what you’re doing will strongly backfire.

## Strategies

- **Layer-based modularization, not a good approch**
  if we modularize the presentation, domain, and data then we have
  - no reusable modules
  - bad work delegation:
    It is hard for devs to work in an isolated environment data
- **Feature-based modularization, _better_**
  - Creating modules based on the features like Onboarding, Tracker, Calories Calculator, etc.
  - One module per feature
  - Size is limited
  - Easier to delegate work
  - Reusable
    You can easily use the onboarding module in another app.
  - It still does not provide a clear separation of concern:
    - We have a tracker feature that contains all the logic we need for tracking the food, so it will contain all the databases where we store the tracked food, it will contain a composable means UI layer, all in the same module, which means the UI layer can access the data layer, which is what we are trying to avoid. What is the solution: **A hybrid modularisation**.
- **Layered-feature modularization, _best_**
  - It is a hybrid of the above two approaches, here we create modules based on the feature and then create further module inside that feature module based on the layers, like domain module, UI module etc.
  - Combines advantages of layer and feature-based modularization

### Calory Tracker app structure:

\ app

- this module will depend on all other modules, it should not be the other way around.

\ buildSrc

- contains build configuration

\ core

- Contains data and classes that are needed in multiple projects.

\ onboarding

- onboarding_domain
- onboarding_presentation

\ tracker

- tracker_data
- tracker_domain
- tracker_presentation
