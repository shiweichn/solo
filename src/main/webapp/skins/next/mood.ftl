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
<link type="text/css" rel="stylesheet" href="${staticServePath}/plugins/mood/assert/mood.css"/>
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
                <#list moods as mood>
                    <li class="comment-list">
                        <div>
                            <div class="comment-meta">
                                <div class="comment-author">
                                    <img class="avatar"
                                    <#--src="https://secure.gravatar.com/avatar/f2a999adb12113e2e1a77ac6081a8999?s=128"-->
                                         src="${mood.userAvatar}"
                                         alt="这个是头像">
                                    <b>${mood.author}</b>
                                    <span>说道：</span>
                                </div>
                                <div class="comment-date">
                                    <time datetime="${mood.createTime}">
                                        ${mood.createTime}
                                    </time>
                                </div>

                            </div>
                            <div class="comment-content">
                                <p>
                                    ${mood.moodContent}
                                    <br>
                                </p>
                            </div>
                            <div class="comment-reply"></div>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>
                <#include "side.ftl">
        <div class="clear"></div>
    </div>
</div>
        <#include "footer.ftl">
</body>
</html>