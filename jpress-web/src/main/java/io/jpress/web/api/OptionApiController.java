/**
 * Copyright (c) 2016-2019, Michael Yang 杨福海 (fuhai999@gmail.com).
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress.web.api;

import com.jfinal.kit.Ret;
import io.jboot.utils.StrUtils;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jpress.core.annotation.NeedAuthentication;
import io.jpress.service.OptionService;
import io.jpress.web.base.ApiControllerBase;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Yang 杨福海 （fuhai999@gmail.com）
 * @version V1.0
 * @Package io.jpress.web.api
 * <p>
 * 用法： http://127.0.0.1:8080/api/option?key=key1,key2
 * <p>
 * 返回数据
 * {
 * state : "ok",
 * data : {
 * key1: "data1",
 * key2: "data2"
 * }
 * }
 */
@RequestMapping("/api/option")
public class OptionApiController extends ApiControllerBase {

    @Inject
    private OptionService optionService;

    @NeedAuthentication
    public void index() {

        String keyPara = getPara("key");

        if (StrUtils.isBlank(keyPara)) {
            renderFailJson("key must not be empty");
            return;
        }

        Map<String, String> data = new HashMap<>();
        Set<String> keys = StrUtils.splitToSet(keyPara, ",");
        for (String key : keys) {
            data.put(key, optionService.findByKey(key));
        }

        renderJson(Ret.ok().set("data", data));
    }
}