package sk.ness.academy.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import sk.ness.academy.dao.AuthorDAO;
import sk.ness.academy.dto.Author;
import sk.ness.academy.dto.AuthorStats;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

  @Resource
  private AuthorDAO authorDAO;

  @Override
  public List<Author> findAll() {
    return this.authorDAO.findAll();
  }


  @Override
  public List<AuthorStats> getAuthorStats() {
    return this.authorDAO.getAuthorStats();
  }

}
