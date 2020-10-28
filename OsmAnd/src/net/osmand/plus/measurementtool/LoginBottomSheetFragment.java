package net.osmand.plus.measurementtool;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import net.osmand.plus.R;
import net.osmand.plus.UiUtilities;
import net.osmand.plus.UiUtilities.DialogButtonType;
import net.osmand.plus.base.MenuBottomSheetDialogFragment;
import net.osmand.plus.base.bottomsheetmenu.simpleitems.DividerSpaceItem;
import net.osmand.plus.base.bottomsheetmenu.simpleitems.ShortDescriptionItem;
import net.osmand.plus.helpers.AndroidUiHelper;
import net.osmand.plus.osmedit.oauth.OsmOAuthAuthorizationAdapter;
import net.osmand.plus.settings.backend.ApplicationMode;
import net.osmand.plus.settings.bottomsheets.OsmLoginDataBottomSheet;

public class LoginBottomSheetFragment extends MenuBottomSheetDialogFragment {

    private ApplicationMode appMode;
    private static final String OSM_LOGIN_DATA = "osm_login_data";

    public static final String TAG = ExitBottomSheetDialogFragment.class.getSimpleName();

    @Override
    public void createMenuItems(Bundle savedInstanceState) {

        items.add(new ShortDescriptionItem.Builder()
                .setDescription(getString(R.string.open_street_map_login_mode))
                .setTitle(getString(R.string.login_open_street_map_org))
                .setLayoutId(R.layout.bottom_sheet_login)
                .create());

        items.add(new DividerSpaceItem(getContext(),
                getResources().getDimensionPixelSize(R.dimen.bottom_sheet_exit_button_margin)));

    }

    @Override
    protected int getDismissButtonTextId() {
        return R.string.shared_string_cancel;
    }


    @Override
    protected int getRightBottomButtonTextId() {
        return R.string.use_login_password;
    }

    @Override
    protected int getThirdBottomButtonTextId() {
        return R.string.sing_in_with_open_street_map;
    }

    @Override
    public int getFirstDividerHeight() {
        return getResources().getDimensionPixelSize(R.dimen.card_content_padding_large);
    }

    @Override
    public int getSecondDividerHeight() {
        return getResources().getDimensionPixelSize(R.dimen.content_padding_small);
    }

    public ApplicationMode getSelectedAppMode() {
        return appMode;
    }

    @Override
    protected void onRightBottomButtonClick() {
        ApplicationMode appMode = getSelectedAppMode();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            OsmLoginDataBottomSheet.showInstance(fragmentManager, OSM_LOGIN_DATA, this, false, appMode);
        }
        dismiss();
    }

    @Override
    protected void onThirdBottomButtonClick() {
        View view = getView();
        OsmOAuthAuthorizationAdapter client = new OsmOAuthAuthorizationAdapter(getMyApplication());
        if (view != null) {
            ViewGroup appBarLayout = view.findViewById(R.id.appbar);
            client.startOAuth(appBarLayout);
        }
        dismiss();
    }

    @Override
    protected DialogButtonType getThirdBottomButtonType() {
        return (DialogButtonType.PRIMARY);
    }

    @Override
    protected DialogButtonType getRightBottomButtonType() {
        return (DialogButtonType.SECONDARY);
    }

    public static void showInstance(@NonNull FragmentManager fragmentManager, @Nullable Fragment targetFragment) {
        if (!fragmentManager.isStateSaved()) {
            LoginBottomSheetFragment fragment = new LoginBottomSheetFragment();
            fragment.setTargetFragment(targetFragment, 0);
            fragment.show(fragmentManager, TAG);
        }
    }
}
