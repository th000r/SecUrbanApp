package de.tudarmstadt.smartcitystudyapp.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import de.tudarmstadt.smartcitystudyapp.R;

import java.util.List;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.SuggestionsViewHolder> {

    public List<String> suggestionsList;

    public SuggestionsAdapter(List<String> suggestionsList) {
        this.suggestionsList = suggestionsList;
    }

    @NonNull
    @Override
    public SuggestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_cardview, parent, false);
        return new SuggestionsViewHolder(view);
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
