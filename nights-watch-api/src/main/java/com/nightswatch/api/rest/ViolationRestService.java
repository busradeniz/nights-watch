package com.nightswatch.api.rest;

import com.nightswatch.api.dto.MediaDto;
import com.nightswatch.api.dto.TagDto;
import com.nightswatch.api.dto.violation.*;

import java.util.Collection;

public interface ViolationRestService {

//    @RequestMapping(method = RequestMethod.GET)
//    Collection<ViolationDto> getAll(@RequestParam("longitude") Double longitude,
//                                    @RequestParam("latitude") Double latitude,
//                                    @RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/top20/mostLiked", method = RequestMethod.GET)
//    Collection<ViolationDto> getMostLikedViolations(@RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/top20/newest", method = RequestMethod.GET)
//    Collection<ViolationDto> getNewestViolations(@RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/top20/owned", method = RequestMethod.GET)
//    Collection<ViolationDto> getOwnedViolations(@RequestParam(value = "violationStatus", required = true) ViolationStatusTypeDto violationStatusTypeDto,
//                                                @RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/top20/watched", method = RequestMethod.GET)
//    Collection<ViolationDto> getWatchedViolations(@RequestParam(value = "violationStatus", required = true) ViolationStatusTypeDto violationStatusTypeDto,
//                                                  @RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
//    ViolationDto get(@PathVariable Long id,
//                     @RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(method = RequestMethod.POST)
//    ViolationDto create(@RequestBody CreateViolationRequestDto createViolationRequestDto,
//                        @RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
//    ViolationDto update(@PathVariable Long id,
//                        @RequestBody UpdateViolationRequestDto updateViolationRequestDto,
//                        @RequestHeader(name = "Authorization", required = false) String token);
//
//    Comment createHistoryComment(Violation violation, User user);
//
//    @RequestMapping(path = "/{id}/addMedia", method = RequestMethod.POST)
//    ViolationDto addMedia(@PathVariable Long id,
//                          @RequestBody MediaDto mediaDto,
//                          @RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/{id}/removeMedia", method = RequestMethod.POST)
//    ViolationDto removeMedia(@PathVariable Long id,
//                             @RequestBody MediaDto mediaDto,
//                             @RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/{id}/addTag", method = RequestMethod.POST)
//    ViolationDto addTag(@PathVariable Long id,
//                        @RequestBody TagDto tagDto,
//                        @RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/{id}/removeTag", method = RequestMethod.POST)
//    ViolationDto removeTag(@PathVariable Long id,
//                           @RequestBody TagDto tagDto,
//                           @RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/{id}/comments", method = RequestMethod.GET)
//    Collection<CommentDto> comments(@PathVariable Long id,
//                                    @RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/{id}/userLikes", method = RequestMethod.GET)
//    Collection<UserLikeDto> userLikes(@PathVariable Long id,
//                                      @RequestHeader(name = "Authorization", required = false) String token);
//
//    @RequestMapping(path = "/{id}/userWatches", method = RequestMethod.GET)
//    Collection<UserWatchDto> userWatches(@PathVariable Long id,
//                                         @RequestHeader(name = "Authorization", required = false) String token);
}
