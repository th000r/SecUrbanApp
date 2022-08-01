package de.tudarmstadt.smartcitystudyapp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

import de.tudarmstadt.smartcitystudyapp.R;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.SuggestionsViewHolder> {

    public List<String> suggestionsList;

    public SuggestionsAdapter(List<String> suggestionsList) {
        this.suggestionsList = suggestionsList;
    }

    @NonNull
    @Override
    public SuggestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_cardview, parent, false);
        SuggestionsViewHolder viewHolder = new SuggestionsViewHolder(view);
        viewHolder.itemView.setOnClickListener(listenerView -> {
            Bundle bundle = new Bundle();
            TextView suggestionTextView = viewHolder.itemView.findViewById(R.id.suggestion_text);
            bundle.putString("suggestion", suggestionTextView.getText().toString());
            bundle.putString("source", suggestionTextView.getText().toString());
            Navigation.findNavController(listenerView).navigate(R.id.action_suggestion_to_submit, bundle);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionsViewHolder holder, int position) {
        holder.suggestion.setText(suggestionsList.get(position));
    }

    @Override
    public int getItemCount() {
        return suggestionsList.size();
    }

    public class SuggestionsViewHolder extends ViewHolder {
        TextView suggestion;

        public SuggestionsViewHolder(View itemView) {
            super(itemView);
            this.suggestion = (TextView) itemView.findViewById(R.id.suggestion_text);
        }
    }
}
