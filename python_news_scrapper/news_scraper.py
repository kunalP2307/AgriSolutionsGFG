import requests
from bs4 import BeautifulSoup
import json
import os
def scrap_news():
    try:
        base_url = 'https://economictimes.indiatimes.com'
        url = 'https://economictimes.indiatimes.com/news/economy/agriculture'
        html_data = requests.get(url)
        parse_data = BeautifulSoup(html_data.text, 'html.parser')
        section_list = parse_data.find(class_="section_list")
        each_story = section_list.find_all(class_="eachStory")
        story_with_image = {'stories':[]}
    
        for story in each_story:
            stories = {}
            stories['story_end_point'] = story.find('a', href=True)['href']
            stories['story_img'] = story.find('img')['data-original']
            story_with_image['stories'].append(stories)
    
        i = 0
        final_news = {"agri_news" : []}
        for story in story_with_image['stories']: 
            print(i)
            news = {}
            html_data = requests.get(f"{base_url}{story['story_end_point']}")
            parse_data = BeautifulSoup(html_data.text, 'html.parser')
            if parse_data.find(class_='artTitle font_faus') != None:
                ## heading
                news['heading'] = parse_data.find(class_='artTitle font_faus').text
                ## time
                news['updated_date_time'] = parse_data.find("time").text
                ## final news
                news['news_description'] = parse_data.find('article').find(class_='artText').text
                news['story_link'] = f"{base_url}{story['story_end_point']}"
                news['story_img_url'] = story['story_img']
                final_news['agri_news'].append(news)
                i+=1
        json_data = json.dumps(final_news)
        
        with open(os.path.join('assets','scrap_data.json'), "w") as file:
            file.write(json_data)
        return 1
    except:
        return 0

print(scrap_news())