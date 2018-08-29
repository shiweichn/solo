/*
 * Solo - A beautiful, simple, stable, fast Java blogging system.
 * Copyright (c) 2010-2018, b3log.org & hacpai.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
/**
 *  about for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.1.4, Feb 18, 2017
 */

/* mood 相关操作 */
admin.moods = {
    currentEditorType: '',
    init: function (fun) {
        this.currentEditorType = Label.editorType;

        $.ajax({
            url: latkeConfig.servePath + "/test.do",
            type: "GET",
            cache: false,
            success: function (result, textStatus) {
                $("#tipMsg").text(result.msg);
                if (!result.sc) {
                    $("#loadMsg").text("");
                    return;
                }
                $("#loadMsg").text("");

                // admin.editors.moodEditor.init()
            }
        });
        console.log('mood page')
        // editor
        admin.editors.moodEditor = new SoloEditor({
            id: "moodContent",
            kind: "all",
            height: 500
        });
        $('#submitMood').click(function () {
            var mood = admin.editors.moodEditor.getContent()
            var requestJSONObject = {
                'mood': {
                    'moodContent': mood
                }
            }
            if (mood.trim().length < 1) {
                console.log('未填写内容，不可发布！')
            } else {
                $.ajax({
                    url: latkeConfig.servePath + "/mood/addMood.do",
                    type: "POST",
                    cache: false,
                    data: JSON.stringify(requestJSONObject),
                    success: function (result, textStatus) {
                        if (result.sc) {
                            $("#loadMsg").text("说说发布成功！");
                        }
                        admin.editors.moodEditor.setContent("")
                    }
                })
            }
        });
    }
};

/*
 * 注册到 admin 进行管理
 */
admin.register["moods"] = {
    "obj": admin.moods,
    "init": admin.moods.init,
    "refresh": function () {
        admin.clearTip();
    }
};
