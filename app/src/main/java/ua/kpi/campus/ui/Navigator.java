package ua.kpi.campus.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import ua.kpi.campus.Config;
import ua.kpi.campus.di.ActivityContext;
import ua.kpi.campus.model.pojo.Bulletin;
import ua.kpi.campus.ui.activity.AddBulletinActivity;
import ua.kpi.campus.ui.activity.BulletinBoardActivity;
import ua.kpi.campus.ui.activity.BulletinBoardModeratorActivity;
import ua.kpi.campus.ui.activity.BulletinContentActivity;
import ua.kpi.campus.ui.activity.EditBulletinActivity;
import ua.kpi.campus.ui.activity.LoginActivity;
import ua.kpi.campus.ui.activity.MainActivity;
import ua.kpi.campus.ui.activity.VotingStudentActivity;

/**
 * Class created to handle all the navigation between activities. This class knows how to open
 * every activity in the application and provides to the client code different methods to start
 * activities with the information needed.
 * <p/>
 * Created by Administrator on 29.01.2016.
 */
public class Navigator {

    private Activity mActivityContext;

    @Inject
    public Navigator(@ActivityContext Context context) {
        mActivityContext = (Activity) context;
    }

    public void startLoginActivity() {
        Intent intent = getLaunchIntent(LoginActivity.class);
        mActivityContext.startActivity(intent);
    }

    public void startMainActivity() {
        Intent intent = getLaunchIntent(MainActivity.class);
        mActivityContext.startActivity(intent);
    }

    public void startBulletinBoardActivity() {
        Intent intent = getLaunchIntent(BulletinBoardActivity.class);
        mActivityContext.startActivity(intent);
    }

    public void startBulletinContentActivity(Bulletin item) {
        Intent intent = getLaunchIntent(BulletinContentActivity.class);
        intent.putExtra(Config.KEY_BULLETIN, item);
        mActivityContext.startActivity(intent);
    }

    public void startNewBulletinActivity() {
        Intent intent = getLaunchIntent(AddBulletinActivity.class);
        mActivityContext.startActivity(intent);
    }

    public void startEditBulletinActivity(Bulletin item) {
        Intent intent = getLaunchIntent(EditBulletinActivity.class);
        intent.putExtra(Config.KEY_BULLETIN, item);
        mActivityContext.startActivity(intent);
    }

    public void startVotingStudentActivity() {
        Intent intent = getLaunchIntent(VotingStudentActivity.class);
        mActivityContext.startActivity(intent);
    }

    /**
     * Generates the intent needed by the client code to launch this activity.
     */
    private Intent getLaunchIntent(Class activityClass) {
        return new Intent(mActivityContext, activityClass);
    }

    public void startBulletinBoardModeratorActivity() {
        Intent intent = getLaunchIntent(BulletinBoardModeratorActivity.class);
        mActivityContext.startActivity(intent);
    }
}
