package skhu.ht.hotthink.api.idea.service;

import skhu.ht.hotthink.api.idea.model.*;

import java.util.List;

public interface FreeService {
    //Free
    public List<FreeListDTO> getFreeList(Pagination pagination);
    //public FreeOutDTO getFree(Long seq, String category);
    public FreeOutDTO getFree(Long seq);
    public boolean setFree(FreeInDTO free, String nickname, String category);
    public boolean putFree(Long seq, String category, FreeInDTO freeInDto);

    //Reply
    public List<ReplyOutDTO> getReplyList(Long frSeq);
    public boolean setReply(ReplyInDTO replyInDTO);
    //public boolean setReply();
    //public boolean putReply();
}
