package tb.sooryagangarajk.com.blogfetcher;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by sooryagangarajk on 14/01/18.
 */

public class CustomArrayAdapter extends ArrayAdapter<DataFish> {


    public CustomArrayAdapter(@NonNull Context context, List<DataFish> list) {
        super(context, R.layout.list_row, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View cview = layoutInflater.inflate(R.layout.list_row, parent, false);

        DataFish dataFish = getItem(position);
        TextView title = (TextView) cview.findViewById(R.id.tid);
        TextView cont = (TextView) cview.findViewById(R.id.contid);
        title.setText(dataFish.dtitle);
//        cont.setText(dataFish.dcontent);

        /// SGK HTML VIEW ////
       /* if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            cont.setText(Html.fromHtml(dataFish.dcontent));
        } else {
            cont.setText(Html.fromHtml(dataFish.dcontent, Html.FROM_HTML_MODE_COMPACT));

        }*/
        return cview;

    }
}
