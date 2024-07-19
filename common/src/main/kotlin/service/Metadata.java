
package service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metadata {

    @SerializedName("usage")
    @Expose
    public Usage usage;

}
