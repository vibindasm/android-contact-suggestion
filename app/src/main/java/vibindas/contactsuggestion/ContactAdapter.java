package vibindas.contactsuggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    Context context;
    int resource, textViewResourceId;
    List<Contact> items, tempItems, suggestions;

    public ContactAdapter(Context context, int resource, int textViewResourceId, ArrayList<Contact> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = new ArrayList<>(items);
        tempItems = new ArrayList<>(items); // this makes the difference.
        suggestions = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_contact_list_item, parent, false);
        }
        Contact contact = items.get(position);
        if (contact != null) {
            TextView lblName = view.findViewById(R.id.name);
            TextView lblNumber = view.findViewById(R.id.number);
            lblName.setText(contact.getName());
            lblNumber.setText(contact.getNumber());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Contact) resultValue).getName() + " - " + ((Contact) resultValue).getNumber();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Contact contact : tempItems) {
                    if (contact.getName().toLowerCase().contains(constraint.toString().toLowerCase())
                            || contact.getNumber().contains(constraint.toString())) {
                        if(validateNumber(contact.getNumber()))
                            suggestions.add(contact);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Contact> filterList = (ArrayList<Contact>) results.values;
            if (results.count > 0) {
                clear();
                for (Contact contact : filterList) {
                    add(contact);
                    notifyDataSetChanged();
                }
            }
        }
    };

    private boolean validateNumber(String contactNumber) {
        String number = contactNumber.replaceAll("\"[\\D]^+ \"", "").replaceAll("\\s", "");
        if(number.startsWith("+65") && number.length() ==11) {
            return number.startsWith("+658") || number.startsWith("+659");
        } else if(number.length() == 8) {
            return number.startsWith("8") || number.startsWith("9");
        } else if(number.startsWith("+") && number.length() < 14) {
            return true;
        }

        return false;

    }
}