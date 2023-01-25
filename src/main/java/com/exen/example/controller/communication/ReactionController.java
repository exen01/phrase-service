package com.exen.example.controller.communication;

import com.exen.example.domain.api.communication.comment.CommentPhraseReq;
import com.exen.example.domain.response.Response;
import com.exen.example.service.communication.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("phrase-service-public/communication/reaction")
public class ReactionController {
    private final ReactionService reactionService;

    @GetMapping("like-phrase/{phraseId}")
    public ResponseEntity<Response> likePhrase(@RequestHeader String accessToken, @PathVariable long phraseId) {
        log.info("Start endpoint likePhrase, accessToken: {}, phraseId: {}", accessToken, phraseId);
        ResponseEntity<Response> response = reactionService.likePhrase(accessToken, phraseId);
        log.info("End endpoint likePhrase, response: {}", response);
        return response;
    }

    @GetMapping("unlike-phrase/{phraseId}")
    public ResponseEntity<Response> deleteLikePhrase(@RequestHeader String accessToken, @PathVariable long phraseId) {
        log.info("Start endpoint deleteLikePhrase, accessToken: {}, phraseId: {}", accessToken, phraseId);
        ResponseEntity<Response> response = reactionService.deleteLikePhrase(accessToken, phraseId);
        log.info("End endpoint deleteLikePhrase, response: {}", response);
        return response;
    }

    @PostMapping("/comment-phrase")
    public ResponseEntity<Response> commentPhrase(@RequestHeader String accessToken, @RequestBody final CommentPhraseReq req) {
        log.info("Start endpoint commentPhrase, accessToken: {}, req: {}", accessToken, req);
        ResponseEntity<Response> response = reactionService.commentPhrase(accessToken, req);
        log.info("End endpoint commentPhrase, response: {}", response);
        return response;
    }

    @DeleteMapping("/delete-comment-phrase/{commentId}")
    public ResponseEntity<Response> deleteCommentPhrase(@RequestHeader String accessToken, @PathVariable long commentId) {
        log.info("Start endpoint deleteCommentPhrase, accessToken: {}, commentId: {}", accessToken, commentId);
        ResponseEntity<Response> response = reactionService.deleteCommentPhrase(accessToken, commentId);
        log.info("Start endpoint deleteCommentPhrase, response: {}", response);
        return response;
    }

    @PostMapping("/block-user/{blockUserId}")
    public ResponseEntity<Response> blockUser(@RequestHeader String accessToken, @PathVariable long blockUserId) {
        log.info("Start endpoint blockUser, accessToken: {}, blockUserId: {}", accessToken, blockUserId);
        ResponseEntity<Response> response = reactionService.blockUser(accessToken, blockUserId);
        log.info("End endpoint blockUser, response: {}", response);
        return response;
    }

    @GetMapping("/blocked-users")
    public ResponseEntity<Response> getBlocUsers(@RequestHeader String accessToken) {
        log.info("Start endpoint getBlocUsers, accessToken: {}", accessToken);
        ResponseEntity<Response> response = reactionService.getBlocUsers(accessToken);
        log.info("End endpoint getBlocUsers, response: {}", response);
        return response;
    }

    @DeleteMapping("/unblock-user/{blockUserId}")
    public ResponseEntity<Response> unblockUser(@RequestHeader String accessToken, @PathVariable long blockUserId) {
        log.info("Start endpoint blockUser, accessToken: {}, blockUserId: {}", accessToken, blockUserId);
        ResponseEntity<Response> response = reactionService.unblockUser(accessToken, blockUserId);
        log.info("End endpoint blockUser, response: {}", response);
        return response;
    }
}
