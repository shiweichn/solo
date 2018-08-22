<#--

    Solo - A beautiful, simple, stable, fast Java blogging system.
    Copyright (c) 2010-2018, b3log.org & hacpai.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

-->
<#include "macro-head.ftl">
<!DOCTYPE html>
<html>
<script src="${staticServePath}/js/lib/jquery/jquery-3.1.0.min.js"></script>
<script src="${staticServePath}/plugins/mood/assert/mood.js"></script>
<link type="text/css" rel="stylesheet" href="${staticServePath}/plugins/mood/assert/mood.css" />
<head>
        <@head title="${blogTitle}">
        <#if metaKeywords??>
        <meta name="keywords" content="${metaKeywords}"/>
        </#if>
        <#if metaDescription??>
        <meta name="description" content="${metaDescription}"/>
        </#if>
        </@head>
</head>
<body>
${topBarReplacement}
        <#include "header.ftl">
<div class="body">
    <div class="wrapper">
        <div class="main">
            <ul>
                <li class="comment-list">
                    <div>
                        <div class="comment-meta">
                            <div class="comment-author">
                                <img class="avatar" src="https://secure.gravatar.com/avatar/f2a999adb12113e2e1a77ac6081a8999?s=128" alt="这个是头像">
                                <b>Simon</b>
                                <span>说道：</span>
                            </div>
                            <div class="comment-date">
                                <time datetime="2018-08-07 08:57">
                                    2018-08-07 08:57
                                </time>
                            </div>

                        </div>
                        <div class="comment-content">
                            <p>
                                OK,用了一个小时，算是把网站加上HTTPS证书了。文章以前添加的附件是http形式加载到文章中去的，一个一个回过头去改，耗费了过多的时间！总归是过度过来了，等着上游DNS广播出去，后天应该就完全没有问题了。

                                <br>
                            </p>
                        </div>
                        <div class="comment-reply"></div>
                    </div>
                </li>
                <li class="comment-list">
                    <div>
                        <div class="comment-meta">
                            这里是一些基本信息
                        </div>
                        <div class="comment-content">
                            这里是正文
                        </div>
                    </div>
                </li>
            </ul>
            <#--<div class="moods">
                <div class="mood_pic">
                    <img width="30px" height="30px"  src="https://secure.gravatar.com/avatar/f2a999adb12113e2e1a77ac6081a8999?s=128" alt="这个是头像">
                </div>
                <div class="mood_contextDiv">
                    <div class="mood_context">
                        这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文这个是正文
                    </div>
                </div>
            </div>-->
        </div>
                <#include "side.ftl">
        <div class="clear"></div>
    </div>
</div>
        <#include "footer.ftl">
</body>
</html>