package in.tkch.androidbootstrap.presenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import in.tkch.androidbootstrap.view.NavigationDrawer;
import mortar.ViewPresenter;

/**
 * Created by takuji on 15/01/12.
 */
@Singleton
public class NavigationDrawerPresenter extends ViewPresenter<NavigationDrawer>{

    @Inject
    public NavigationDrawerPresenter() {
    }

    public void openDrawer() {
        getView().openDrawer();
    }

    public void closeDrawer() {
        getView().closeDrawer();
    }
}
