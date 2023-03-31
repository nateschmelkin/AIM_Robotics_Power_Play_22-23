# AIM Robotics FTC Project

This repository contains the code for AIM Robotics' 2022-2023.

## Downloading Android Studio

To develop and run the code, you need to download Android Studio. You can download the latest version of Android Studio from the official website [here](https://developer.android.com/studio).

After downloading Android Studio, follow the installation instructions for your operating system.

## Downloading the Repository

Create a new directory for the repository on your local machine.

To download the global repository, click on the green "Code" button on the GitHub page and select "Download ZIP" to download it as a ZIP file.

Alternatively, you can copy the repository URL and use the `git clone https://github.com/nateschmelkin/AIM_Robotics_Power_Play_22-23.git` command in the new folder to clone the repository to your local machine.

Make sure that the repositry is now in the local directory.

## Connecting to the Global Repository

Before you can push changes to the global repository or pull changes from it, you need to connect your local repository to the global one. To do this, follow these steps:

1. Open the terminal on your local machine.
2. Navigate to the directory where you cloned the repository by running this command: `cd location/of/the/directory`
3. Run the following command to add the global repository as a remote: `git remote add origin AIM-Robotics-2023-2024`
4. Verify that the remote has been added by running the following command: `git remote -v`

This should list the global repository as `origin`.


## Updating the Repository

To update your local copy of the repository with the latest changes from the global repository, use the `git pull` command. This will merge any changes that have been made to the global repository into your local copy.

Once you have set up the repository in Android Studio, you can also click the "Pull" button in the "Version Control" tab to update your local copy with the latest changes from the global repository.


## Committing Changes

To save your changes to the repository, you need to commit them using the `git commit` command.

Alternatively, you can also click the "Commit" button in the "Version Control" tab in Android Studio to commit your changes.


## Pushing Changes

Once you have committed your changes, you can push them to the global repository using the `git push` command. This will upload your changes to the global repository, making them available to other contributors.

Alternatively, you can also click the "Push" button in the "Version Control" tab in Android Studio to push your changes to the global repository.