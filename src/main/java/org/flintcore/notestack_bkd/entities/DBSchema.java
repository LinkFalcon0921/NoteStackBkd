package org.flintcore.notestack_bkd.entities;

// Contiene nombres de tablas, esquema y constraints generales
public interface DBSchema {
    String SCHEMA_NAME = "public"; // Esquema de Supabase

    String CREATED_AT = "created_at";
    String UPDATED_AT = "updated_at";

    String PRIMARY_ID_JOIN_NAME = "id";

    // Nombres de columnas para la entidad User
    interface UserColumns {
        String TABLE = "users";
        String NAME = "name";
        String ID = "user_id";
    }

    interface RefreshTokenColumns {
        String TABLE = "refresh_tokens",
        ID = "refresh_token_id";
    }

    interface NoteColumns {

        String TABLE = "notes",
                ID = "note_id",
        OWNER_ID = "owner_id",
        GROUP_ID = GroupColumns.ID,
        IS_ENABLED = "is_enabled",
        NOTE_MAPPED_BY = "note";

    }

    interface NotePermissionColumns {
        String TABLE = "note_permissions";
    }

    interface ReminderColumns {
        String TABLE = "reminders",
        ID = "reminder_id",
                ISSUED_AT_COLUMN = "trigger_on";
    }

    interface GroupColumns {
        String TABLE = "groups",
        ID = "group_id";

        String GROUP_MEMBERS_BY = "group",
        NOTE_MAPPED_BY = "group";
    }

    interface GroupMemberColumns {
        String TABLE = "group_members";
        String ID = "group_member_id";

        // Map Ids
        String USER_ID = "userId";
        String GROUP_ID = "groupId";

    }

    // Nombres de columnas para OAuthProvider
    interface AuthProviderColumns {
        String TABLE = "auth_providers";

        String EMAIL = "email";

        String PROVIDER_TYPE = "provider_type";
        String PROVIDER_USER_TOKEN = "provider_token";

        /* OAuth */
        String UK_OAUTH_PROVIDER = "uk_provider_type_user_id";

        String OAUTH_TABLE = "oauth_providers";

        String USER_MAPPED_BY = "user";
    }
}


