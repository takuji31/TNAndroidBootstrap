package in.tkch.androidbootstrap.activity;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import flow.Backstack;
import flow.Flow;
import in.tkch.androidbootstrap.screen.MainScreen;
import in.tkch.androidbootstrap.view.NavigationDrawer;
import in.tkch.androidbootstrap.presenter.NavigationDrawerPresenter;
import in.tkch.androidbootstrap.R;
import mortar.dagger2support.Dagger2;


public class MainActivity extends BaseActivity implements Flow.Dispatcher {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    @InjectView(R.id.navigationDrawer)
    NavigationDrawer navigationDrawer;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Inject
    NavigationDrawerPresenter drawerPresenter;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mTitle = getTitle();
        setSupportActionBar(toolbar);

        // Set up the drawer.
        navigationDrawer.setUp(this, toolbar, drawerLayout);
    }

    @Override
    protected String getScopeName() {
        return MainScreen.class.getName();
    }

    @Override
    protected Class<?> getComponentClass() {
        return MainScreen.Component.class;
    }

    @Override
    protected void onInject() {
        Dagger2.<MainScreen.Component>get(this).inject(this);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawer.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback traversalCallback) {
        Backstack origin = traversal.origin;
        Backstack destination = traversal.destination;

    }
}
