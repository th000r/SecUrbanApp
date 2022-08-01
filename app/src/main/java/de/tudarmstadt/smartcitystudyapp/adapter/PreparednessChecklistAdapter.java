package de.tudarmstadt.smartcitystudyapp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.smartcitystudyapp.R;
import de.tudarmstadt.smartcitystudyapp.models.PreparednessChecklistModel;

public class PreparednessChecklistAdapter extends RecyclerView.Adapter<PreparednessChecklistAdapter.SuggestionsViewHolder> {

    ArrayList<PreparednessChecklistModel> suggestionsList;

    public PreparednessChecklistAdapter(ArrayList<PreparednessChecklistModel> checklist_items) {
        this.suggestionsList = checklist_items;
    }

    @NonNull
    @Override
    public SuggestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_preparedness_checklist, parent, false);
        SuggestionsViewHolder viewHolder = new SuggestionsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionsViewHolder holder, int position) {
        holder.checkListName.setText(suggestionsList.get(position).getName());
        holder.checkBoxDone.setChecked(suggestionsList.get(position).getDone());
        holder.checkBoxToDo.setChecked(suggestionsList.get(position).getTodo());
    }

    @Override
    public int getItemCount() {
        return suggestionsList.size();
    }

    public class SuggestionsViewHolder extends ViewHolder {
        TextView checkListName;
        CheckBox checkBoxDone;
        CheckBox checkBoxToDo;

        public SuggestionsViewHolder(View itemView) {
            super(itemView);
            this.checkListName = (TextView) itemView.findViewById(R.id.preparedness_checklist_name);
            this.checkBoxDone = (CheckBox) itemView.findViewById(R.id.preparedness_checklist_done);
            this.checkBoxToDo = (CheckBox) itemView.findViewById(R.id.preparedness_checklist_todo);
        }
    }
}
