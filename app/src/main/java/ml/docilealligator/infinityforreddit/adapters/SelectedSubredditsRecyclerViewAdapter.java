package ml.docilealligator.infinityforreddit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ml.docilealligator.infinityforreddit.activities.BaseActivity;
import ml.docilealligator.infinityforreddit.customtheme.CustomThemeWrapper;
import ml.docilealligator.infinityforreddit.databinding.ItemSelectedSubredditBinding;
import ml.docilealligator.infinityforreddit.multireddit.ExpandedSubredditInMultiReddit;

public class SelectedSubredditsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final BaseActivity activity;
    private final CustomThemeWrapper customThemeWrapper;
    private final ArrayList<ExpandedSubredditInMultiReddit> subreddits;

    public SelectedSubredditsRecyclerViewAdapter(BaseActivity activity, CustomThemeWrapper customThemeWrapper, ArrayList<ExpandedSubredditInMultiReddit> subreddits) {
        this.activity = activity;
        this.customThemeWrapper = customThemeWrapper;
        if (subreddits == null) {
            this.subreddits = new ArrayList<>();
        } else {
            this.subreddits = subreddits;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubredditViewHolder(ItemSelectedSubredditBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SubredditViewHolder) {
            ((SubredditViewHolder) holder).binding.subredditNameItemSelectedSubreddit.setText(subreddits.get(holder.getBindingAdapterPosition()).getName());
            ((SubredditViewHolder) holder).binding.deleteImageViewItemSelectedSubreddit.setOnClickListener(view -> {
                subreddits.remove(holder.getBindingAdapterPosition());
                notifyItemRemoved(holder.getBindingAdapterPosition());
            });
        }
    }

    @Override
    public int getItemCount() {
        return subreddits.size();
    }

    public void addSubreddits(ArrayList<ExpandedSubredditInMultiReddit> newSubreddits) {
        int oldSize = subreddits.size();
        subreddits.addAll(newSubreddits);
        notifyItemRangeInserted(oldSize, newSubreddits.size());
    }

    public void addUserInSubredditType(String username) {
        subreddits.add(new ExpandedSubredditInMultiReddit(username, null));
        notifyItemInserted(subreddits.size());
    }

    public ArrayList<ExpandedSubredditInMultiReddit> getSubreddits() {
        return subreddits;
    }

    class SubredditViewHolder extends RecyclerView.ViewHolder {
        ItemSelectedSubredditBinding binding;

        public SubredditViewHolder(@NonNull ItemSelectedSubredditBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.subredditNameItemSelectedSubreddit.setTextColor(customThemeWrapper.getPrimaryIconColor());
            binding.deleteImageViewItemSelectedSubreddit.setColorFilter(customThemeWrapper.getPrimaryIconColor(), android.graphics.PorterDuff.Mode.SRC_IN);

            if (activity.typeface != null) {
                binding.subredditNameItemSelectedSubreddit.setTypeface(activity.typeface);
            }
        }
    }
}
