
package service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usage {

    @SerializedName("prompt_tokens")
    @Expose
    public Integer promptTokens;
    @SerializedName("prompt_unit_price")
    @Expose
    public String promptUnitPrice;
    @SerializedName("prompt_price_unit")
    @Expose
    public String promptPriceUnit;
    @SerializedName("prompt_price")
    @Expose
    public String promptPrice;
    @SerializedName("completion_tokens")
    @Expose
    public Integer completionTokens;
    @SerializedName("completion_unit_price")
    @Expose
    public String completionUnitPrice;
    @SerializedName("completion_price_unit")
    @Expose
    public String completionPriceUnit;
    @SerializedName("completion_price")
    @Expose
    public String completionPrice;
    @SerializedName("total_tokens")
    @Expose
    public Integer totalTokens;
    @SerializedName("total_price")
    @Expose
    public String totalPrice;
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("latency")
    @Expose
    public Float latency;

}
