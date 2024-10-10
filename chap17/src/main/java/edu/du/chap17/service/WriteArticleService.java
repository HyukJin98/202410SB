package edu.du.chap17.service;

import edu.du.chap17.dao.ArticleDao;
import edu.du.chap17.model.Article;
import edu.du.chap17.model.WritingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;

@Service
public class WriteArticleService {
	@Autowired
	ArticleDao articleDao;

	private static WriteArticleService instance = new WriteArticleService();
	public static WriteArticleService getInstance() {
		return instance;
	}

	private WriteArticleService() {
	}

	public Article write(WritingRequest writingRequest)
			throws IdGenerationFailedException {

		int groupId = IdGenerator.getInstance().generateNextId("article");

		Article article = writingRequest.toArticle();

		article.setGroupId(groupId);
		article.setPostingDate(new Date());
		DecimalFormat decimalFormat = new DecimalFormat("0000000000");
		article.setSequenceNumber(decimalFormat.format(groupId) + "999999");


			int articleId = ArticleDao.insert(article);
			if (articleId == -1) {
				throw new RuntimeException("DB ���� ����: " + articleId);
			}

			article.setId(articleId);
			return article;
		}
		}
	}
}
