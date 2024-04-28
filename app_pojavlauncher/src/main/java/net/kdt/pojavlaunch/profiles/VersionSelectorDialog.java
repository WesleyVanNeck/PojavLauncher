package net.kdt.pojavlaunch.profiles;

import static net.kdt.pojavlaunch.extra.ExtraCore.getValue;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AlertDialog;

import net.kdt.pojavlaunch.JMinecraftVersionList;
import net.kdt.pojavlaunch.R;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.ui.adapter.VersionListAdapter;

public class VersionSelectorDialog {

    /**
     * Opens the version selector dialog
     *
     * @param context                  The context of the activity
     * @param hideCustomVersions      Whether to hide custom versions or not
     * @param versionSelectorListener The listener for version selection
     */
    public static void open(Context context, boolean hideCustomVersions, VersionSelectorListener versionSelectorListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ExpandableListView expandableListView = (ExpandableListView) LayoutInflater.from(context)
                .inflate(R.layout.dialog_expendable_list_view, null);

        JMinecraftVersionList jMinecraftVersionList = (JMinecraftVersionList) getValue(ExtraConstants.RELEASE_TABLE);
        JMinecraftVersionList.Version[] versionArray;
        if (jMinecraftVersionList == null || jMinecraftVersionList.versions == null) {
            versionArray = new JMinecraftVersionList.Version[0];
        } else {
            versionArray = jMinecraftVersionList.versions;
        }

        VersionListAdapter adapter = new VersionListAdapter(versionArray, hideCustomVersions, context);
        expandableListView.setAdapter(adapter);
        builder.setView(expandableListView);
        AlertDialog dialog = builder.show();

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String version = adapter.getChild(groupPosition, childPosition);
            versionSelectorListener.onVersionSelected(version, adapter.isSnapshotSelected(groupPosition));
            dialog.dismiss();
            return true;
        });
    }
}
