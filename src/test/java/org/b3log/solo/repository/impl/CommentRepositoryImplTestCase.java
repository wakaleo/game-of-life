/*
 * Copyright (c) 2010-2018, b3log.org & hacpai.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.b3log.solo.repository.impl;

import java.util.Date;
import java.util.List;
import org.b3log.latke.repository.Transaction;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.model.Comment;
import org.b3log.solo.repository.CommentRepository;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link ArticleRepositoryImpl} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Jan 7, 2012
 */
@Test(suiteName = "repository")
public class CommentRepositoryImplTestCase extends AbstractTestCase {

    /**
     * Adds successfully.
     * 
     * @throws Exception exception
     */
    @Test
    public void add() throws Exception {
        final CommentRepository commentRepository = getCommentRepository();

        final JSONObject comment = new JSONObject();

        comment.put(Comment.COMMENT_CONTENT, "comment1 content");
        comment.put(Comment.COMMENT_DATE, new Date());
        comment.put(Comment.COMMENT_EMAIL, "test@gmail.com");
        comment.put(Comment.COMMENT_NAME, "comment1 name");
        comment.put(Comment.COMMENT_ON_ID, "comment1 on id");
        comment.put(Comment.COMMENT_ON_TYPE, "comment1 on type");
        comment.put(Comment.COMMENT_ORIGINAL_COMMENT_ID, "");
        comment.put(Comment.COMMENT_ORIGINAL_COMMENT_NAME, "");
        comment.put(Comment.COMMENT_SHARP_URL, "comment1 sharp url");
        comment.put(Comment.COMMENT_URL, "comment1 url");
        comment.put(Comment.COMMENT_THUMBNAIL_URL, "comment1 thumbnail url");

        final Transaction transaction = commentRepository.beginTransaction();
        commentRepository.add(comment);
        transaction.commit();

        final List<JSONObject> comments =
                commentRepository.getComments("comment1 on id", 1,
                                              Integer.MAX_VALUE);
        Assert.assertNotNull(comments);
        Assert.assertEquals(comments.size(), 1);

        Assert.assertEquals(
                commentRepository.getComments("not found", 1, Integer.MAX_VALUE).
                size(), 0);
        
        Assert.assertEquals(commentRepository.getRecentComments(3).size(), 1);
    }
}
