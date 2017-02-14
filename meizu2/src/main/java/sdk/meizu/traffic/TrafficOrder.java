package sdk.meizu.traffic;

import com.meizu.account.pay.OutTradeOrderInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class TrafficOrder extends OutTradeOrderInfo {
    public TrafficOrder(JSONObject jSONObject) {
        try {
            if (jSONObject.has("p")) {
                setPartner(jSONObject.getString("p"));
            }
            if (jSONObject.has("i")) {
                setOutTrade(jSONObject.getString("i"));
            }
            if (jSONObject.has("u")) {
                setNotifyUrl(jSONObject.getString("u"));
            }
            if (jSONObject.has("s")) {
                setSign(jSONObject.getString("s"));
            }
            if (jSONObject.has("st")) {
                setSignType(jSONObject.getString("st"));
            }
            if (jSONObject.has("a")) {
                setPayAccounts(jSONObject.getString("a"));
            }
            if (jSONObject.has("sj")) {
                setSubject(jSONObject.getString("sj"));
            }
            if (jSONObject.has("ec")) {
                setExtContent(jSONObject.getString("ec"));
            }
            if (jSONObject.has("bd")) {
                setBody(jSONObject.getString("bd"));
            }
            if (jSONObject.has("tp")) {
                setTotalFee(jSONObject.getString("tp"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "Partner:" + getPartner() + " | NotifyUrl:" + getNotifyUrl() + " | Sign:" + getSign() + " | signType:" + getSignType() + " | account:" + getPayAccounts();
    }
}
