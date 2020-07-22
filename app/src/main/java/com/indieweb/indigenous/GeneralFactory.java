package com.indieweb.indigenous;

import android.content.Context;

import androidx.annotation.NonNull;

import com.indieweb.indigenous.indieweb.IndiewebGeneral;
import com.indieweb.indigenous.mastodon.MastodonGeneral;
import com.indieweb.indigenous.model.User;
import com.indieweb.indigenous.pixelfed.PixelfedGeneral;

import static com.indieweb.indigenous.mastodon.MastodonReader.CHANNEL_NAME_MASTODON_ANONYMOUS;
import static com.indieweb.indigenous.pixelfed.PixelfedReader.CHANNEL_NAME_PIXELFED_ANONYMOUS;
import static com.indieweb.indigenous.users.AuthActivity.MASTODON_ACCOUNT_TYPE;
import static com.indieweb.indigenous.users.AuthActivity.PIXELFED_ACCOUNT_TYPE;

public class GeneralFactory {

    @NonNull
    public static General getGeneral(User user, String channelId, Context context) {
        String type = "indieweb";

        if (channelId != null) {
            switch (channelId) {
                case CHANNEL_NAME_MASTODON_ANONYMOUS:
                    type = "mastodon";
                    break;
                case CHANNEL_NAME_PIXELFED_ANONYMOUS:
                    type = "pixelfed";
                    break;
            }
        }

        if (user.isAuthenticated()) {
            if (user.getAccountType().equals(PIXELFED_ACCOUNT_TYPE)) {
                type = "pixelfed";
            }
            if (user.getAccountType().equals(MASTODON_ACCOUNT_TYPE)) {
                type = "mastodon";
            }
        }

        switch (type) {
            case "indieweb":
                return new IndiewebGeneral(context, user);
            case "pixelfed":
                return new PixelfedGeneral(context, user);
            case "mastodon":
                return new MastodonGeneral(context, user);
        }

        return null;
    }

}
