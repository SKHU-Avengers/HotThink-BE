package skhu.ht.hotthink.api.idea.service;

import skhu.ht.hotthink.api.domain.Free;
import skhu.ht.hotthink.api.domain.Idea;
import skhu.ht.hotthink.api.idea.model.FreeInDTO;
import skhu.ht.hotthink.api.idea.model.FreeListDTO;
import skhu.ht.hotthink.api.idea.model.FreeOutDTO;
import skhu.ht.hotthink.api.idea.model.Pagination;

import java.util.List;
/*
        작성자: 홍민석
        작성일: 19-10-04
        내용: HotThink, RealThink, FreeThink에서 사용하는 기능정의.
     */
public interface IdeaService {

    public List<FreeListDTO> getFreeList(Pagination pagination);
    public FreeOutDTO getFree(Long seq, String category);
    public boolean setFree(FreeInDTO free, String nickname, String category);
    public boolean putFree(Long seq, String category, FreeInDTO freeInDto);
    public List<Idea> getRealList(Pagination pagination);
    public Idea getReal(int seq, String category);
}
