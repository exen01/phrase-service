package com.exen.example.service.communication;

import com.exen.example.dao.common.CommonDao;
import com.exen.example.dao.communication.ReactionDao;
import com.exen.example.dao.communication.SubscriptionDao;
import com.exen.example.domain.api.communication.reaction.comment.CommentPhraseReq;
import com.exen.example.domain.api.communication.reaction.getBlockUsers.GetBlockUsersResp;
import com.exen.example.domain.constant.Code;
import com.exen.example.domain.dto.WhoseComment;
import com.exen.example.domain.response.Response;
import com.exen.example.domain.response.SuccessResponse;
import com.exen.example.domain.response.error.Error;
import com.exen.example.domain.response.error.ErrorResponse;
import com.exen.example.service.common.CommonService;
import com.exen.example.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ValidationUtils validationUtils;
    private final CommonDao commonDao;
    private final CommonService commonService;
    private final ReactionDao reactionDao;
    private final SubscriptionDao subscriptionDao;

    /**
     * Likes phrase
     *
     * @param accessToken user access token
     * @param phraseId    phrase id
     * @return response
     */
    @Override
    public ResponseEntity<Response> likePhrase(String accessToken, long phraseId) {
        validationUtils.validationDecimalMin("phraseId", phraseId, 1);
        long userId = commonDao.getUserIdByAccessToken(accessToken);

        commonService.checkBlockByPhraseId(userId, phraseId);

        reactionDao.likePhrase(userId, phraseId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    /**
     * Remove like from phrase
     *
     * @param accessToken user access token
     * @param phraseId    phrase id
     * @return response
     */
    @Override
    public ResponseEntity<Response> deleteLikePhrase(String accessToken, long phraseId) {
        validationUtils.validationDecimalMin("phraseId", phraseId, 1);
        long userId = commonDao.getUserIdByAccessToken(accessToken);
        reactionDao.deleteLikePhrase(userId, phraseId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    /**
     * Comments phrase
     *
     * @param accessToken user access token
     * @param req         phrase id and comment text
     * @return response
     */
    @Override
    public ResponseEntity<Response> commentPhrase(String accessToken, CommentPhraseReq req) {
        validationUtils.validationRequest(req);
        long userId = commonDao.getUserIdByAccessToken(accessToken);

        commonService.checkBlockByPhraseId(userId, req.getPhraseId());

        reactionDao.commentPhrase(userId, req);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    /**
     * Delete user comment or comment under user phrase
     *
     * @param accessToken user access token
     * @param commentId   comment id
     * @return response
     */
    @Override
    public ResponseEntity<Response> deleteCommentPhrase(String accessToken, long commentId) {
        validationUtils.validationDecimalMin("commentId", commentId, 1);
        long userId = commonDao.getUserIdByAccessToken(accessToken);

        WhoseComment whoseComment = reactionDao.whoseComment(commentId);
        log.info("userId: {}, whoseComment: {}", userId, whoseComment);

        if (whoseComment.getCommentUserId() == userId || whoseComment.getPhraseUserId() == userId) {
            reactionDao.deleteCommentPhrase(commentId);
            return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder().code(Code.NOT_YOUR_COMMENT)
                    .userMessage("Это не ваш комментарий и не комментарий к вашей фразе.")
                    .build()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Blocked user with blockUserId
     *
     * @param accessToken user access token
     * @param blockUserId blocked user ID
     * @return response
     */
    @Override
    public ResponseEntity<Response> blockUser(String accessToken, long blockUserId) {
        validationUtils.validationDecimalMin("blockUserId", blockUserId, 1);
        long userId = commonDao.getUserIdByAccessToken(accessToken);

        if (userId == blockUserId) {
            return new ResponseEntity<>(ErrorResponse.builder().error(Error.builder()
                            .code(Code.NOT_BLOCK_YOURSELF)
                            .userMessage("Вы не можете заблокировать сами себя").build())
                    .build(), HttpStatus.BAD_REQUEST);
        }

        reactionDao.blockUser(userId, blockUserId);
        subscriptionDao.unsubscription(blockUserId, userId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }

    /**
     * Gets blocked users
     *
     * @param accessToken user access token
     * @return list of blocked users
     */
    @Override
    public ResponseEntity<Response> getBlocUsers(String accessToken) {
        long userId = commonDao.getUserIdByAccessToken(accessToken);
        return new ResponseEntity<>(SuccessResponse.builder()
                .data(GetBlockUsersResp.builder().blockedUsers(reactionDao.getBlockUsers(userId)).build()).build(), HttpStatus.OK);
    }

    /**
     * Unblocked user with blockUserId
     *
     * @param accessToken user access token
     * @param blockUserId unblocked user id
     * @return response
     */
    @Override
    public ResponseEntity<Response> unblockUser(String accessToken, long blockUserId) {
        validationUtils.validationDecimalMin("blockUserId", blockUserId, 1);
        long userId = commonDao.getUserIdByAccessToken(accessToken);
        reactionDao.unblockUser(userId, blockUserId);
        return new ResponseEntity<>(SuccessResponse.builder().build(), HttpStatus.OK);
    }
}
