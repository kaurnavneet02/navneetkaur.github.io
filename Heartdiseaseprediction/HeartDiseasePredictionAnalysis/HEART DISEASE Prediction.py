#!/usr/bin/env python
# coding: utf-8

# # Data Preparation

# In[7]:


#Import libraries
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import seaborn as sns

get_ipython().run_line_magic('matplotlib', 'inline')

import warnings
warnings.filterwarnings('ignore')


# In[8]:


#Load the dataset and store the date into a dataframe variable df
hd = pd.read_csv("G:\clevelanddataset.csv")


# In[9]:


#Check number of rows and columns in a dataframe
hd.shape


# In[10]:


#Change the column names
hd.columns = ['Age', 'Sex','ChestpainType','RestingBP','Cholestrol',
              'FastingBloodSugar','RestingECG','Thalach','Exang',
              'Oldpeak','Slope','Ca','Thal','Target']


# In[11]:


#print dataframe
hd.head()


# In[12]:


#Summary of the dataframe
hd.info()


# # Data Cleaning

# In[13]:


#Return the number of unique values in the attributes of dataframe and compare the values from the Dataset Info
hd.nunique()


# In[14]:


hd['Ca'].unique()


# In[15]:


#Get the unique values of an attribute 'Thal'
hd['Thal'].unique()


# In[16]:


hd1 = pd.read_csv("G:/clevelanddataset.csv",na_values=['?'], header = 0,
names=['Age', 'Sex','ChestpainType','RestingBP','Cholestrol','FastingBloodSugar'
       ,'RestingECG','Thalach','Exang','Oldpeak','Slope','Ca','Thal','Target'])                                                           


# In[17]:


#Check number of missing values in a dataframe
hd1.isnull().sum()


# In[18]:


#Filter missing values in to a dataframe
missing = hd1[hd1.isnull().any(axis=1)]


# In[19]:


missing


# In[20]:


#Replace 2,3,4 values with 1 as the (1,2,3,4) values of target attribute predict the presence of heart disease
hd1['Target'] = hd['Target'].replace([2,3,4],1)


# In[21]:


#Values have been replaced by 1 = presence of disease
hd1


# In[22]:


hd1['Ca'].value_counts()


# In[23]:


hd1['Ca'].fillna(hd1['Ca'].value_counts().index[0], inplace = True)


# In[24]:


hd1['Ca'].value_counts() 


# In[25]:


#Get maximum value counts in attribute
hd1['Thal'].value_counts()


# In[26]:


#Fill the missing values with the maximum occuring value
hd1['Thal'].fillna(hd1['Thal'].value_counts().index[0], inplace = True)


# In[27]:


#Missing value filled with maximum occuring value
hd1['Thal'].value_counts()


# In[28]:


#Check number of missing values in a dataframe
hd1.isnull().sum()


# In[29]:


#Check number of unique values in the attribute
hd1['Target'].unique()


# In[30]:


#display top 6 rows
hd1.head(6)


# In[31]:


#Check feature information
hd1.info()


# In[32]:


# convert 'float64' into 'int64' type
hd1['Ca'] = hd1['Ca'].astype('int')


# In[33]:


# convert 'float64' into 'int64' type
hd1['Thal'] = hd1['Thal'].astype('int')


# In[34]:


#Converted datatypes
hd1.info()


# In[35]:


#Check for duplicates
hd1.duplicated()


# In[36]:


#plot a box plot to check outliers
hd1.plot(kind='box', subplots=True, layout=(3,6), sharex=False, sharey=False, figsize=(15, 10), color='red');


# In[37]:


#remove outliers for RestingBP using IQR method
Q3 = hd1.RestingBP.quantile(.75)
Q1 = hd1.RestingBP.quantile(.25)
IQR = Q3 - Q1
hd1 = hd1[~((hd1.RestingBP < Q1 - 1.5*IQR) | (hd1.RestingBP > Q3 + 1.5*IQR))]


# In[38]:


#remove outliers for Cholestrol using IQR method
Q3 = hd1.Cholestrol.quantile(.75)
Q1 = hd1.Cholestrol.quantile(.25)
IQR = Q3 - Q1
hd1 = hd1[~((hd1.Cholestrol < Q1 - 1.5*IQR) | (hd1.Cholestrol > Q3 + 1.5*IQR))]


# In[39]:


#remove outliers for Oldpeak using IQR method
Q3 = hd1.Oldpeak.quantile(.75)
Q1 = hd1.Oldpeak.quantile(.25)
IQR = Q3 - Q1
hd1 = hd1[~((hd1.Oldpeak < Q1 - 1.5*IQR) | (hd1.Oldpeak > Q3 + 1.5*IQR))]


# In[40]:


#Statistics Summary
hd1.describe()


# # Exploring continuous features

# In[41]:


#Copy hd1 dataframe to cont to explore continuous features
cont=hd1.copy()


# In[42]:


cont


# In[43]:


# Drop all categorical features
cat_feat = ['Sex','ChestpainType', 'FastingBloodSugar', 'RestingECG', 'Exang', 'Slope', 'Ca', 'Thal']
cont.drop(cat_feat, axis=1, inplace=True)
cont.head()


# In[44]:


cont.describe()


# In[45]:


cont.groupby('Target').mean()


# In[46]:


cont.groupby(cont['Age'].isnull()).mean()


# In[47]:


for i in ['Age','Thalach', 'Cholestrol']:
    No_disease = list(cont[cont['Target'] == 0][i].dropna())
    Disease_present = list(cont[cont['Target'] == 1][i].dropna())
    xmin = min(min(No_disease), min(Disease_present))
    xmax = max(max(No_disease), max(Disease_present))
    width = (xmax - xmin) / 40
    sns.distplot(No_disease, color='r', kde=False, bins=np.arange(xmin, xmax, width))
    sns.distplot(Disease_present, color='g', kde=False, bins=np.arange(xmin, xmax, width))
    plt.legend(['No_disease', 'Disease_present'])
    plt.title('Overlaid histogram for {}'.format(i))
    plt.show()


# In[48]:


for i, col in enumerate(['RestingBP','Oldpeak']):
    plt.figure(i)
    sns.catplot(x=col, y='Target', data=cont, kind='point', aspect=3)


# # Exploring Categorical Features

# In[49]:


#Copy hd1 dataframe to cont to explore categorical features
cat=hd1.copy()


# In[50]:


#Replace categorical variables for better representation
cat['Sex'] = cat.Sex.replace({1: "Male", 0: "Female"})


# In[51]:


# Drop all continuous features
cont_feat = ['Age', 'RestingBP', 'Cholestrol','Thalach' , 'Oldpeak']
cat.drop(cont_feat, axis=1, inplace=True)
cat.head()


# In[52]:


cat.info()


# In[53]:


cat.groupby('Target').mean()


# In[54]:


#Plot categorical features
for i, col in enumerate(['Sex', 'Thal', 'Exang', 'Ca']):
    plt.figure(i)
    sns.catplot(x=col, y='Target', data=cat, kind='point', aspect=2, )


# In[55]:


cat.pivot_table('Target', index='Sex',columns='Exang', aggfunc='count')


# In[56]:


sns.catplot(x='Exang',y='Target',kind='point',data=cat,col='Sex')
pass


# In[57]:


cat.pivot_table('Target', index='Sex',columns='Ca', aggfunc='count')


# In[58]:


sns.catplot(x='Ca',y='Target',kind='point',data=cat,col='Sex')
pass


# In[59]:


cat.pivot_table('Target', index='Sex',columns='Thal', aggfunc='count')


# In[60]:


sns.catplot(x='Thal',y='Target',kind='point',data=cat,col='Sex')
pass


# # Data Visualization

# In[61]:


hd1.hist(figsize = (14,14))
plt.show()


# In[62]:


#Visualize the count of number of patients with absence or presence of stages of heart disease

#Create data for the graph
NoDisease = len(hd1[hd1.Target == 0])
Presence = len(hd1[hd1.Target == 1])
plt.figure(figsize=(8,6))
#Data to plot
labels = 'Disease Present','No Disease'
sizes = [NoDisease,Presence]
colors = ['Red', 'yellow']
explode = (0, 0)  # explode 1st slice
#Plot
plt.pie(sizes, explode=explode, labels=labels, colors=colors,autopct='%1.1f%%', shadow=True, startangle=90)
plt.axis('equal')
plt.show()


# In[63]:


#visualize the count of number of patients with absence or presence of stages of heart disease
#Count plot on two categorical variables
sns.barplot(x='Sex',y ='Age',hue ='Target',data=hd1, palette = "hsv")
plt.title('Heart disease based on Sex')
plt.xlabel("Sex (0 = Female , 1= Male)")
plt.show()


# In[64]:


#crosstab function to plot at what age range frequency does a person get heart disease
pd.crosstab(hd1.Age,hd1.Target).plot(kind="bar",figsize=(18,6))
plt.title('Heart Disease Frequency for Ages')
plt.xlabel('Age')
plt.ylabel('Frequency')
plt.savefig('HeartDiseaseAgevsTarget.png')
plt.show()


# In[65]:


#Heart Disease with respect to Thal
pd.crosstab(hd1.Thal,hd1.Target).plot(kind="bar",figsize=(10,5),color=['orange','blue'])
plt.title('Heart Disease with respect to Thal')
plt.xlabel('Thal ( 3 = normal; 6 = fixed defect; 7 = reversible defect)')
plt.legend(["No disease", "Presence of Disease"])
plt.ylabel('Frequency')
plt.show()


# In[66]:


#Heart Disease with respect to RestingBP
pd.crosstab(hd1.RestingBP,hd1.Target).plot(kind="bar",figsize=(18,6))
plt.title('Heart Disease Frequency for Ages')
plt.xlabel('RestingBP')
plt.ylabel('Frequency')
plt.savefig('HeartDiseaseRestingBPvsTarget.png')
plt.show()


# In[67]:


#Heart Disease with respect to ChestpainType
pd.crosstab(hd1.ChestpainType,hd1.Target).plot(kind="bar",figsize=(12,6),color=['#11A5AA','#AA1190' ])
plt.title('Heart Disease Frequency with respect to Chest Pain Type')
plt.xlabel('ChestPainType (Value 1: typical angina, Value 2: atypical angina, Value 3: non-anginal pain, Value 4: asymptomatic)')
plt.ylabel('Frequency of Disease or Not')
plt.show()


# In[68]:


#Heart Disease with respect to FastingBloodSugar
pd.crosstab(hd1.FastingBloodSugar,hd1.Target).plot(kind="bar",figsize=(12,6),color=['#DAF7A6','#FF5733'])
plt.title('Heart Disease Frequency According To FastingBloodSugar')
plt.xlabel('FBS - (Fasting Blood Sugar > 120 mg/dl) (1 = true; 0 = false)')
plt.legend(["No Disease", "Disease Present"])
plt.ylabel('Frequency of Disease or Not')
plt.show()


# In[69]:


#Heart Disease with respect to Exang
pd.crosstab(hd1.Exang,hd1.Target).plot(kind="bar",figsize=(12,6),color=['green','blue' ])
plt.title('Heart Disease Frequency with respect To Exang')
plt.xlabel('(Exercise induced angina (1 = yes; 0 = no)')
plt.legend(["No Disease", "Disease Present"])
plt.ylabel('Frequency of Disease or Not')
plt.show()


# In[70]:


#crosstab function to plot at what Ca range frequency does a person get heart disease
pd.crosstab(hd1.Ca,hd1.Target).plot(kind="bar",figsize=(18,6))
plt.title('Heart Disease Frequency for Ca')
plt.xlabel('Ca: number of major vessels (0-3) colored by flourosopy')
plt.ylabel('Frequency')
plt.savefig('HeartDiseaseCavsTarget.png')
plt.show()


# In[71]:


#scatter plot for Thalach vs. Target variable
plt.scatter(x=hd1.Age[hd1.Target==1], y=hd1.Thalach[(hd1.Target==1)], c="red")
plt.scatter(x=hd1.Age[hd1.Target==0], y=hd1.Thalach[(hd1.Target==0)])
plt.legend(["Presence", "No Disease"])
plt.xlabel("Age")
plt.ylabel("Maximum Heart Rate")
plt.show()


# # Feature Selection

# In[72]:


#Visualize the data  
plt.figure(figsize=(12,10))
#plot heat map and get correlations of each features in dataset
sns.heatmap(hd1.corr(),annot=True, cmap="RdBu")
plt.show()


# In[73]:


#Correlation with target
hd1.drop('Target', axis=1).corrwith(hd1.Target).plot(kind='bar', grid=True, figsize=(14, 6), title="Correlation with target")


# In[74]:


hd2 = hd1.copy()


# In[75]:


#Convert categorical variables into dummy variables
Sex_dmy = pd.get_dummies(hd2['Sex'], prefix='Sex',drop_first=True)
ChestpainType_dmy = pd.get_dummies(hd2['ChestpainType'],prefix='ChestpainType', drop_first=True)
FastingBloodSugar_dmy = pd.get_dummies(hd2['FastingBloodSugar'],prefix='FastingBloodSugar', drop_first=True)
RestECG_dmy = pd.get_dummies(hd2['RestingECG'],prefix='RestingECG', drop_first=True)
Exang_dmy = pd.get_dummies(hd2['Exang'],prefix='Exang', drop_first=True)
Slope_dmy = pd.get_dummies(hd2['Slope'],prefix='Slope', drop_first=True)
Ca_dmy = pd.get_dummies(hd2['Ca'],prefix='Ca', drop_first=True)
Thal_dmy = pd.get_dummies(hd2['Thal'],prefix='Thal', drop_first=True)


# In[76]:


hd3 = pd.concat([hd2,Sex_dmy,ChestpainType_dmy,FastingBloodSugar_dmy,RestECG_dmy,Exang_dmy,Slope_dmy,Ca_dmy,Thal_dmy], axis=1)


# In[77]:


hd3.head()


# In[78]:


hd4 = hd3.drop(['Sex', 'ChestpainType', 'FastingBloodSugar', 'RestingECG', 'Exang', 'Slope', 'Ca', 'Thal'],axis=1)


# In[79]:


hd4.head()


# In[80]:


hd4.describe()


# # Data Preprocessing

# In[81]:


#Use the StandardScaler from sklearn to scale my dataset
from sklearn.preprocessing import StandardScaler
standardScaler = StandardScaler()


# In[82]:


feature_scale = ['Age','RestingBP','Cholestrol','Thalach','Oldpeak']


# In[83]:


hd4[feature_scale] = standardScaler.fit_transform(hd4[feature_scale])


# In[84]:


hd4.head()


# In[85]:


#Split our dataset into training and testing datasets
X =  hd4.drop(['Target'],axis=1)
y = hd4['Target']


# In[86]:


from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X,y,test_size=0.2, random_state=0)


# In[87]:


len(X_train)


# In[88]:


len(X_test)


# In[89]:


from sklearn.model_selection import GridSearchCV
from sklearn.ensemble import RandomForestClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.linear_model import LogisticRegression


# In[90]:


#Random Forest Classifier and K-Nearest Neighbours Classifier
model_param = {
'RandomForestClassifier':{
        'model':RandomForestClassifier(),
        'param':{
            'n_estimators': [20,50,80,120,150]
        }
    },
        'KNeighborsClassifier':{
        'model':KNeighborsClassifier(),
        'param':{
            'n_neighbors': [5,10,15,20,25]
        }
            
            
    }
}


# In[91]:


scores =[]
for model_name, mp in model_param.items():
    model_selection = GridSearchCV(estimator=mp['model'],param_grid=mp['param'],cv=5,return_train_score=False)
    model_selection.fit(X,y)
    scores.append({
        'model': model_name,
        'best_score': model_selection.best_score_,
        'best_params': model_selection.best_params_
    })


# In[92]:


df_model_score = pd.DataFrame(scores,columns=['model','best_score','best_params'])
df_model_score


# In[93]:


model_rfc = RandomForestClassifier(n_estimators = 150)


# In[94]:


model_rfc.fit(X_train,y_train)


# In[95]:


model_rfc.score(X_test,y_test)


# In[96]:


#Confusion Matrix
from sklearn.metrics import confusion_matrix
cm = confusion_matrix(y_test,model_rfc.predict(X_test))
cm


# In[97]:


#plot the graph
from matplotlib import pyplot as plt
import seaborn as sn
sn.heatmap(cm, annot=True)
plt.xlabel('Predicted')
plt.ylabel('True Value')
plt.show()


# In[98]:


#Logical Regression
from sklearn.linear_model import LogisticRegression
log = LogisticRegression()


# In[99]:


params = {'penalty':['l1','l2'],
         'C':[0.01,0.1,1,10,100],
         'class_weight':['balanced',None]}
log_model = GridSearchCV(log,param_grid=params,cv=10)


# In[100]:


log_model.fit(X_train,y_train)

# Printing best parameters choosen through GridSearchCV
log_model.best_params_


# In[101]:


predict = log_model.predict(X_test)


# In[102]:


from sklearn.metrics import accuracy_score
print('Accuracy Score: ',accuracy_score(y_test,predict))
print('Using Logistic Regression we get an accuracy score of: ',
      round(accuracy_score(y_test,predict),5)*100,'%')


# In[103]:


from sklearn.metrics import recall_score,precision_score,classification_report,roc_auc_score,roc_curve
print(classification_report(y_test,predict))


# In[104]:


method = ["Random Forest" ,"KNN", "Logical Regression"]
accuracy = [82.45,78.59,85.97]

sns.set_style("whitegrid")
plt.figure(figsize=(16,5))
plt.yticks(np.arange(0,100,10))
plt.ylabel("Accuracy %")
plt.xlabel("Algorithms")
sns.barplot(x=method, y=accuracy, palette="rocket")
plt.show()

