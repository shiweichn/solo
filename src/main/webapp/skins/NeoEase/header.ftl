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
<div class="header">
    <div class="wrapper">
        <div class="left">
            <h1>
                <a class="title" href="${servePath}">
                    ${blogTitle}
                </a>
            </h1>
            <span class="sub-title">${blogSubtitle}</span>
        </div>
        <form class="right" action="${servePath}/search">
            <input id="search" type="text" name="keyword" />
            <input type="submit" value="" class="none" />
        </form>
        <div class="clear"></div>
    </div>
</div>
<div class="nav">
    <div class="wrapper">
        <ul>
            <li>
                <a rel="nofollow" href="${servePath}/">${indexLabel}</a>
            </li>
            <#list pageNavigations as page>
            <li>
                <a href="${page.pagePermalink}" target="${page.pageOpenTarget}"><#if page.pageIcon != ''><img class="page-icon" src="${page.pageIcon}"></#if>${page.pageTitle}</a>
            </li>
            </#list>  
            <li>
                <a href="${servePath}/tags.html">${allTagsLabel}</a>  
            </li>
            <li>
                <a rel="alternate" href="${servePath}/blog-articles-rss.do">RSS<img src="${staticServePath}/images/feed.png" alt="RSS"/></a>
            </li>
        </ul>
        <div class="right">
            <span class="translate-ico" onclick="goTranslate()"></span>
            <div class="right">
                ${viewCount1Label}
                <span class="tip">
                    ${statistic.statisticBlogViewCount}
                </span>
                &nbsp;&nbsp;
                ${articleCount1Label}
                <span class="tip">
                    ${statistic.statisticPublishedBlogArticleCount}
                </span>
                &nbsp;&nbsp;
                ${commentCount1Label}
                <span class="tip">
                    ${statistic.statisticPublishedBlogCommentCount}
                </span>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
