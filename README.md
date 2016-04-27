# SpeedLessApp
Repository for the SpeedLess application.
README

In the event that the Master branch is not the most up to date one, the substitute master branch is:
-Morning+A-M-planA


This project/product prototype currently consists of this set of components and functionality:

---A Raspberry Pi:
-Handles the lights
-Quickly configurable, such that it will pair with a (currently Android) smartphone over BT automatically after booting.

---A SpeedLess (Android) app:
-The app exists and has a main screen interface
-A dud Log-in screen. (Not tied to any real functionality, its use WILL break program state by disrupting the unsecured activity lifecycle of the main activity)
-Functionality for accessing ‘nvdb’ (big thanks) to retrieve the speed limit for a road stretch associated with a coordinate pair.
-Functionality for Bluetooth. Automatically connects with paired Raspberry Pi, no user interaction necessary at this stage. (Prerequisites: Paired with Pi using phone and Pi GUI, phone bluetooth setting toggled ON).
-A service with multithreading. The use of an android service separates its contents from the activity lifecycle of the rest of the app. This means that bluetooth, nvdb access, evt GPS use, evt other database access can persist independently from what goes on on screen; the android activities. The use of multithreading herein allows these services to run in parallel, blocking calls will not slow or halt other parts of the project/program.
-The app currently simulates both speed (would evt be calculated from GPS, or measured by a car computer) and a route of coordinates (again evt covered by GPS). The speed limit is, as stated, retrieved from nvdb.
-A button on the main screen allows users to toggle the SpeedLess lights on and off. A holdover is that it grays out the other dud menu options, functionality meant to tie into a logged in/logged out state.

This project/product prototype was conceived to also contain:
-A component for GPS usage, for purposes mentioned above.
-A user component, enabling users to log in to store and track stats.
-A database component to facilitate the above component.
-A gamification component where users could compare themselves to others users.



Current notices / disclaimers:
-The Project CAN be demoed in its current state.
Yet, it is likely far from bug free, and quite unstable. Case in point, activity lifecycles and their effect on program state are largely unaccounted for. As such, it is currently more of a one-screen app. Restarts are encouraged.

-In the event this project is somehow implemented with a real car whilst near its current iteration, its use is highly discouraged. We bear no knowledge of safety standards, and the project is nothing more than an iteration of a prototype. Do not consider it a final/viable product.

-Whilst not spaghetti code, nor complex or lacking comments, the current code body is in something of a sketch format. There are no javadoc style comments for documentation,the code contains vestigial methods, code is commented out and adherence to good standards and practices is touch and go. Lastly, as per implementation of most features, it should be painfully obvious to most readers that the project was coded by amateurs learning to code for android and its dependencies as they went along, oft coding first and reading questions later.


Conceivable adaptations:

-Various parts of the system, simulated or otherwise, could be replaced by / incorporated with a car computer.

-The Pi could be forgone if one were to use the phone to display the lights on screen, though this carries it’s own weaknesses and drawbacks. The tradeoff would be a single app; low user investment cost.

