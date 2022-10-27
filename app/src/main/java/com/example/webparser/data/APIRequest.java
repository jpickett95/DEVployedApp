package com.example.webparser.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIRequest {
    String mUrl;
    public APIRequest(String url){
        mUrl = url;
    }

    public JSONObject getJson(){
        String json = "{\"message\":\"not found\"}";

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(mUrl)
                    .get()
                    .addHeader("cookie", "preferred_locale=en-US; advertising_id=a297ac8e-99f0-4ad0-bc46-e0821b81afbb; analytics_id=18823c0f-9561-46ab-a82c-93aefc81fb2f; amazon_jobs_session=aFc5ZkZVaGJudkpuUkg1N1lWVmdQeEtMSmtUaFJmai9pTjRWMVc5UzJPL3lmMjRhVEtLZFlFMkw3SnpwODR2S0VSb0l0VHlMLzQrcE9IZWh5UURKaGRZTjcveTl3TXRkanU2VVlSS3lUUmFBMUg1Nmo2L2RKTTErZHdsTjFRR2x2M21sZlNaYnBzVXBzSWFsV3B4Z3dGUmF6V2JicTdQUG9JaUlqcGhKeDRycWJjcEtQU2RDT2xKMDhacXMvTUFQS2hKbVBwaWdCYnpsOVkrR2UvQkozVmFJUERpS0loOUh5aE96TWlKSDY0ND0tLS9tT2hjT256WjRZQWZGQ2pBSXNFN2c9PQ%253D%253D--46950093f7a38865ad5092d3f3829f3199bc2798")
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("Accept-Language", "en-US,en;q=0.8")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Cookie", "preferred_locale=en-US; advertising_id=5002122b-30a6-4e96-bc12-5e41fabf71e4; analytics_id=08999f17-d5fc-4cb3-9f5a-e5bf820afeef; AMCVS_CCBC879D5572070E7F000101^%^40AdobeOrg=1; _ga=GA1.2.1322197531.1664326074; csm-sid=018-5184653-1533926; AMCV_CCBC879D5572070E7F000101^%^40AdobeOrg=-1124106680^%^7CMCIDTS^%^7C19286^%^7CMCMID^%^7C14357093989904797610803846268855996191^%^7CMCAID^%^7CNONE^%^7CMCOPTOUT-1666237946s^%^7CNONE^%^7CvVersion^%^7C5.2.0; _gat=1; amazon_jobs_session=NVYxNm1ZRjJEekgwcFJYb1BlUEhJMVJsbStmUElrQTFjdTVPNk9IK3NvTEJXcHhld1cyR2E2WStVeUM2V25FOG5RZ2pvUHFGb2cxTmJKWTdtTWRLSkJ5Ykk3cWp6YnRUaHkyY2lqUU1MaEdtRXhVa1B3cnppU2NYaW5uUjVqeHZyWUJYV0pBMHpJbVNseUpJWkJCZFVlSkZCSGdMaTl3TEQ1VWhDdXh5cmtWeExybWMzYnJTMFlUOUhrWTJ1VHQ0ZmQxL1BrOUlwRGZaWUxkU0JRQi9hWmNNUVo2elYxNE5LeTJtdnNGNTE1alFsV2FTNVVwVjc4cXJ5amEyK2I5dnNoUkt4SXpZN1Jsck9QYUlBT3lvcGE1ZkV5UDZ0ckptNzdrczZLcFdzMHg0cE1vQWYvZnVVeTVUTG5RdmhDMWtxR09Pa3o5dk9xTlozaDR0NnlXdTA3VDNMK3RxdXBkYjVnOVcxZ20yZDUwPS0tMUU5ZURyRW1TSm16bW9BVUJEZ3pYZz09--014e677278d113ac6532cde3d3e3e41bc952890e")
                    //.addHeader("If-None-Match", "W/^\^b6e3e42ab2a0ab88aec53849aec00d8a-gzip^^")
                    .addHeader("Referer", "https://www.amazon.jobs/en/search?offset=0&result_limit=10&sort=relevant&category^%^5B^%^5D=operations-it-support-engineering&category^%^5B^%^5D=project-program-product-management-technical&category^%^5B^%^5D=project-program-product-management-non-tech&category^%^5B^%^5D=software-development&distanceType=Mi&radius=24km&latitude=38.89037&longitude=-77.03196&loc_group_id=&loc_query=United^%^20States&base_query=&city=&country=USA&region=&county=&query_options=&")
                    .addHeader("Sec-Fetch-Dest", "empty")
                    .addHeader("Sec-Fetch-Mode", "cors")
                    .addHeader("Sec-Fetch-Site", "same-origin")
                    .addHeader("Sec-GPC", "1")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36")
                    .build();

            Response response = client.newCall(request).execute();
            json = response.body().string();

            response.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return  jsonObject;

    }
}
