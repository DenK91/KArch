package digital.neuron.karch.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import digital.neuron.karch.data.Config

@Entity(tableName = Config.QUESTION_TABLE_NAME)
data class Question(
        @SerializedName("question_id") @PrimaryKey var id: Long,
        @SerializedName("owner") @Embedded(prefix = "owner_") var user: User?,
        @SerializedName("creation_date") @ColumnInfo(name = "creation_date") var creationDate: Long,
        @SerializedName("title") var title: String?,
        @SerializedName("link") var link: String?) {
    constructor() : this(0, null, 0, null, null)
}

data class User(@SerializedName("user_id") var id: Long = 0,
                @SerializedName("display_name") var name: String? = null,
                @SerializedName("profile_image") var image: String? = null,
                @SerializedName("link") var link: String? = null)