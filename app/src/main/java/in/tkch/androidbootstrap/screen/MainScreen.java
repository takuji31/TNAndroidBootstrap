package in.tkch.androidbootstrap.screen;

import javax.inject.Singleton;

import in.tkch.androidbootstrap.view.NavigationDrawer;
import in.tkch.androidbootstrap.activity.MainActivity;

/**
 * Created by takuji on 15/01/12.
 */
public final class MainScreen {
    @dagger.Component
    @Singleton
    public static interface Component {
        void inject(MainActivity t);
        void inject(NavigationDrawer t);
    }
}
